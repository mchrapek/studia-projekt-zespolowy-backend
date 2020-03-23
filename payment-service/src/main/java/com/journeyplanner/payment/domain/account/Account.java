package com.journeyplanner.payment.domain.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Value
@Builder
@Document(collection = "account")
class Account {

    @Id
    @NonNull
    String id;

    @Indexed(unique = true)
    @NonNull
    String email;

    @NonNull
    BigDecimal balance;
}
