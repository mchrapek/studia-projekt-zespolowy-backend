package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class AccountCreator {

    Account emptyAccount(String email) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .lastEventTime(Instant.now().minus(1, ChronoUnit.DAYS))
                .email(email)
                .balance(BigDecimal.ZERO)
                .build();
    }
}
