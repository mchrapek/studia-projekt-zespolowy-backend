package com.journeyplanner.common.config.events;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferEvent implements Event {

    @NonNull String id;

    @NonNull String email;

    @NonNull String paymentId;

    @NonNull BigDecimal value;

    @NonNull TransferType type;

    @NonNull Instant createdTime;
}
