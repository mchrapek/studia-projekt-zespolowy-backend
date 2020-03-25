package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

class AccountHistoryCreator {

    AccountHistory chargeEvent(final String accountId, final BigDecimal value) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(Instant.now())
                .type(AccountEventType.CHARGE)
                .value(value)
                .build();
    }

    AccountHistory loadEvent(final String accountId, final Transfer transfer) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(transfer.getEventTime())
                .type(AccountEventType.LOAD)
                .value(transfer.getValue())
                .build();
    }

    AccountHistory returnEvent(final String accountId, final Transfer transfer) {
        return AccountHistory.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdTime(transfer.getEventTime())
                .type(AccountEventType.RETURN)
                .value(transfer.getValue())
                .build();
    }
}
