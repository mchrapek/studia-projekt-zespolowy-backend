package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.events.TransferType;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.payment.exceptions.IllegalOperation;
import com.journeyplanner.payment.exceptions.NoPermission;
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import com.journeyplanner.payment.infrastructure.output.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class AccountFacade {

    private final AccountRepository accountRepository;
    private final AccountCreator creator;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransferRepository transferRepository;
    private final AccountHistoryCreator accountHistoryCreator;
    private final MailSender mailSender;

    public AccountDto getAccountByEmail(final String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(email)));

        return AccountDto.from(account, accountHistoryRepository.findAllByAccountId(account.getId()));
    }

    public void savePendingTransfer(final Transfer transfer) {
        transferRepository.save(transfer);
    }

    public String chargeAccount(final String email, final ChargeAccountRequest request) {
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(email)));

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().add(request.getValue()));
        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.chargeEvent(account.getId(), request.getValue()));

        mailSender.publish(SendMailEvent.builder().to(email).templateName(Template.ACCOUNT_CHARGED.getPath())
                .params(new HashMap<String, String>() {{
                    put("value", request.getValue().toPlainString());
                }}).build());

        log.info(format("Account charged : {0} : {1} : {2}", account.getId(), account.getEmail(), account.getBalance()));
        return accountHistory.getId();
    }

    public String loadTransfer(final Transfer transfer) {
        Account account = accountRepository.findByEmail(transfer.getEmail())
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(transfer.getEmail())));

        if (account.getBalance().compareTo(transfer.getValue()) < 0) {
            transferRepository.findAndModifyStatus(transfer.getId(), TransferStatus.ERROR);
            log.info(format("You don't have enough money : {0} : {1}", transfer.getId(), transfer.getEmail()));
            throw new IllegalOperation(format("You don't have enough money : {0} : {1}", transfer.getId(), transfer.getEmail()));
        }

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().subtract(transfer.getValue()));

        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.loadEvent(account.getId(), transfer));

        mailSender.publish(SendMailEvent.builder().to(transfer.getEmail()).templateName(Template.PAYMENT_LOAD.getPath())
                .params(new HashMap<String, String>() {{
                    put("value", transfer.getValue().toPlainString());
                }}).build());

        log.info(format("Account loaded : {0} : {1} : {2}", account.getId(), account.getEmail(), account.getBalance()));
        return accountHistory.getId();
    }

    public String returnTransfer(final Transfer transfer) {
        Account account = accountRepository.findByEmail(transfer.getEmail())
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(transfer.getEmail())));

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().add(transfer.getValue()));

        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.returnEvent(account.getId(), transfer));

        mailSender.publish(SendMailEvent.builder().to(transfer.getEmail()).templateName(Template.PAYMENT_RETURN.getPath())
                .params(new HashMap<String, String>() {{
                    put("value", transfer.getValue().toPlainString());
                }}).build());


        log.info(format("Account return : {0} : {1} : {2}", account.getId(), account.getEmail(), account.getBalance()));
        return accountHistory.getId();
    }

    public void retryTransaction(final String email, final String paymentId) {
        Transfer transfer = transferRepository.findFirstByPaymentIdOrderByEventTimeDesc(paymentId)
                .orElseThrow(() -> new IllegalOperation(format("Cannot find transaction for payment : {0}", paymentId)));

        if (!transfer.getEmail().equals(email)) {
            throw new NoPermission(format("You don't have permission for transaction : {0}", transfer.getId()));
        }

        if (transfer.getStatus() != TransferStatus.ERROR) {
            log.warn(format("You cannot retry transaction : {0} : in status : {1}", transfer.getId(), transfer.getStatus()));
            throw new IllegalOperation(format("Transaction already in progress : {0}", transfer.getId()));
        }

        log.info(format("Retry transfer request : {0} : for paymentId : {1}", transfer.getId(), paymentId));
        savePendingTransfer(Transfer.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .paymentId(paymentId)
                .status(TransferStatus.PENDING)
                .eventTime(Instant.now())
                .type(TransferType.LOAD)
                .value(transfer.getValue())
                .build());
    }

    public TransferDto getTransactionStatus(String email, String paymentId) {
        Transfer transfer = transferRepository.findFirstByPaymentIdOrderByEventTimeDesc(paymentId)
                .orElseThrow(() -> new IllegalOperation(format("Cannot find payment {0}", paymentId)));

        if (!transfer.getEmail().equals(email)) {
            throw new NoPermission(format("You don't have permission for transaction : {0}", transfer.getId()));
        }

        return TransferDto.from(transfer);
    }
}
