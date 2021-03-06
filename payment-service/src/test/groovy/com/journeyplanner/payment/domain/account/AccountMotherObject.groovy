package com.journeyplanner.payment.domain.account

import java.time.Instant
import java.time.temporal.ChronoUnit

class AccountMotherObject {

    static aAccount(String email, BigDecimal balance = BigDecimal.ZERO) {
        Account.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .balance(balance)
                .build()
    }
}
