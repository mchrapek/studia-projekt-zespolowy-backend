package com.journeyplanner.common.config.events;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationEvent implements Event {

    @NonNull String id;

    @NonNull String email;

    @NonNull String journeyId;

    @NonNull BigDecimal price;

    @NonNull Instant eventTimeStamp;
}
