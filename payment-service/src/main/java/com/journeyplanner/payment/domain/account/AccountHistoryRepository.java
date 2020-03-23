package com.journeyplanner.payment.domain.account;

import org.springframework.data.repository.Repository;

import java.util.List;

interface AccountHistoryRepository extends Repository<AccountHistory, String> {

    AccountHistory save(AccountHistory accountHistory);

    List<AccountHistory> findAllByAccountId(String accountId);

    void deleteAll();
}
