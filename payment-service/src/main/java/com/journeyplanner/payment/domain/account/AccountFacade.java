package com.journeyplanner.payment.domain.account;

import com.journeyplanner.payment.exceptions.IllegalOperation;
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AccountFacade {

    private final AccountRepository accountRepository;
    private final AccountCreator creator;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransferRepository transferRepository;
    private final AccountHistoryCreator accountHistoryCreator;

    public AccountDto getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(email)));

        return AccountDto.from(account, accountHistoryRepository.findAllByAccountId(account.getId()));
    }

    public void savePendingTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public String chargeAccount(String email, ChargeAccountRequest request) {
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(email)));

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().add(request.getValue()));
        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.chargeEvent(account.getId(), request.getValue()));

        return accountHistory.getId();
    }

    public String loadTransfer(Transfer transfer) {
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

    public String returnTransfer(Transfer transfer) {
        Account account = accountRepository.findByEmail(transfer.getEmail())
                .orElseGet(() -> accountRepository.save(creator.emptyAccount(transfer.getEmail())));

        accountRepository.modifyAccountBalance(account.getId(), account.getBalance().add(transfer.getValue()));

        AccountHistory accountHistory = accountHistoryRepository
                .save(accountHistoryCreator.returnEvent(account.getId(), transfer));

        return accountHistory.getId();
    }
}
