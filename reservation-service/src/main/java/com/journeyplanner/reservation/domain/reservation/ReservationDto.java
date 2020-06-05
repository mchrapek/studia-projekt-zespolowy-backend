package com.journeyplanner.reservation.domain.reservation;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class ReservationDto {

    String id;
    ReservationStatus status;
    String email;
    String journeyId;
    String journeyName;
    BigDecimal price;
    Instant createdTime;
    String paymentId;
    Instant start;
    Instant end;

    static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .email(reservation.getEmail())
                .journeyId(reservation.getJourneyId())
                .journeyName(reservation.getJourneyName())
                .price(reservation.getPrice())
                .createdTime(reservation.getCreatedTime())
                .paymentId(reservation.getPaymentId())
                .start(reservation.getStart())
                .end(reservation.getEnd())
                .build();
    }
}
