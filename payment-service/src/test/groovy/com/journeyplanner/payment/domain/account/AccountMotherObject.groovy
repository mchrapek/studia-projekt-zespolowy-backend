package com.journeyplanner.payment.domain.account

class AccountMotherObject {

    static aAccount(String email, BigDecimal balance = BigDecimal.ZERO, events = new ArrayList()) {
        Account.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .balance(balance)
                .events(events)
                .build()
    }
}
