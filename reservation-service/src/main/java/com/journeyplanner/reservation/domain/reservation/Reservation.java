package com.journeyplanner.reservation.domain.reservation;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Document(collection = "reservation")
class Reservation {

    @Id
    @NonNull String id;

    @NonNull ReservationStatus status;

    @NonNull String email;

    @NonNull String journeyId;

    @NonNull String journeyName;

    @NonNull BigDecimal price;

    @NonNull Instant start;

    @NonNull Instant end;

    @NonNull Instant createdTime;

    @Indexed(unique = true)
    @NonNull String paymentId;
}
