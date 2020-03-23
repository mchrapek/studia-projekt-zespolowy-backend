package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.TransferType;
import com.journeyplanner.payment.exceptions.IllegalOperation;
import com.journeyplanner.payment.exceptions.NoPermission;
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class AccountFacade {

    private final AccountRepository accountRepository;
    private final AccountCreator creator;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransferRepository transferRepository;
    private final AccountHistoryCreator accountHistoryCreator;

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

        return accountHistory.getId();
    }

    public String loadTransfer(final Transfer transfer) {
        Account account = accountRepository.findByEmail(transfer.getEmail())
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(transfer.getEmail())));

        if (account.getBalance().compareTo(transfer.getValue()) < 0) {
            transferRepository.findAndModifyStatus(transfer.getId(), TransferStatus.ERROR);
            throw new IllegalOperation("Cannot process");
        }

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().subtract(transfer.getValue()));

        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.loadEvent(account.getId(), transfer));

        return accountHistory.getId();
    }

    public String returnTransfer(final Transfer transfer) {
        Account account = accountRepository.findByEmail(transfer.getEmail())
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(transfer.getEmail())));

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().add(transfer.getValue()));

        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.returnEvent(account.getId(), transfer));

        return accountHistory.getId();
    }

    public void retryTransaction(final String email, final String paymentId) {
        Transfer transfer = transferRepository.findFirstByPaymentIdOrderByEventTimeDesc(paymentId)
                .orElseThrow(() -> new IllegalOperation("Cannot find transaction"));

        if (!transfer.getEmail().equals(email)) {
            throw new NoPermission("You don't have permission");
        }

        if (transfer.getStatus() != TransferStatus.ERROR) {
            throw new IllegalOperation("Transaction already in progress");
        }

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
                .orElseThrow(() -> new IllegalOperation("Cannot find transaction"));

        if (!transfer.getEmail().equals(email)) {
            throw new NoPermission("You don't have permission");
        }

        return TransferDto.from(transfer);
    }
}
