package com.journeyplanner.payment.domain.account;

import com.journeyplanner.payment.infrastructure.output.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    AccountFacade accountFacade(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, TransferRepository transferRepository, MailSender mailSender) {
        return new AccountFacade(accountRepository, new AccountCreator(), accountHistoryRepository, transferRepository, new AccountHistoryCreator(), mailSender);
    }
}
