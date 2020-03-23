package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.TransferType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Document(collection = "transfer")
public class Transfer {

    @NonNull String id;

    @NonNull String email;

    @NonNull String paymentId;

    @NonNull BigDecimal value;

    @NonNull TransferType type;

    @NonNull TransferStatus status;

    @NonNull Instant eventTime;
}
