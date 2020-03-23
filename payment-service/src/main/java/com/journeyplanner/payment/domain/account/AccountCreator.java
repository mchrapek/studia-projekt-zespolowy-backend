package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.util.UUID;

class AccountCreator {

    Account emptyAccount(String email) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .balance(BigDecimal.ZERO)
                .build();
    }
}
