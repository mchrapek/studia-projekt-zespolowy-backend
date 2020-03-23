package com.journeyplanner.payment.domain.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    AccountFacade accountFacade(AccountRepository accountRepository) {
        return new AccountFacade(accountRepository, new AccountCreator());
    }
}
