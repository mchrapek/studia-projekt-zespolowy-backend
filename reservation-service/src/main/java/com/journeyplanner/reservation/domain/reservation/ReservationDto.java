package com.journeyplanner.reservation.domain.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    Instant createdTime;
    String paymentId;

    static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .email(reservation.getEmail())
                .journeyId(reservation.getJourneyId())
                .price(reservation.getPrice())
                .createdTime(reservation.getCreatedTime())
                .paymentId(reservation.getPaymentId())
                .build();
    }
}
