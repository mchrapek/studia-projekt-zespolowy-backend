package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

class AccountHistoryCreator {

    AccountHistory chargeEvent(String accountId, BigDecimal value) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(Instant.now())
                .type(AccountEventType.CHARGE)
                .value(value)
                .build();
    }

    AccountHistory loadEvent(String accountId, Transfer transfer) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(transfer.getEventTime())
                .type(AccountEventType.LOAD)
                .value(transfer.getValue())
                .build();
    }

    AccountHistory returnEvent(String accountId, Transfer transfer) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(transfer.getEventTime())
                .type(AccountEventType.RETURN)
                .value(transfer.getValue())
                .build();
    }
}
