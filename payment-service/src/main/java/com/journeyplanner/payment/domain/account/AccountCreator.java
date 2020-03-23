package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class AccountCreator {

    Account emptyAccount(String email) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .balance(BigDecimal.ZERO)
                .events(new ArrayList<>())
                .build();
    }
}
