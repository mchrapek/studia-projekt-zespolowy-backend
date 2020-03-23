package com.journeyplanner.payment.domain.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Document("accountHistory")
class AccountHistory {

    @NonNull
    String id;

    @NonNull
    String accountId;

    @NonNull
    Instant createdTime;

    @NonNull
    AccountEventType type;

    @NonNull
    BigDecimal value;
}
