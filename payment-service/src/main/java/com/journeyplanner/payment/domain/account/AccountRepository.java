package com.journeyplanner.payment.domain.account;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AccountRepository extends Repository<Account, String>, AccountCustomRepository {

    Optional<Account> findByEmail(String email);

    Account save(Account account);

    void deleteAll();
}
