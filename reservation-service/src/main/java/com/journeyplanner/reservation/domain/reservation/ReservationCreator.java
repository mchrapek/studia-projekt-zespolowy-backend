package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CreateReservationEvent;

import java.time.Instant;
import java.util.UUID;

class ReservationCreator {

    Reservation from(CreateReservationEvent event) {
        return Reservation.builder()
                .id(UUID.randomUUID().toString())
                .email(event.getEmail())
                .name(event.getName())
                .price(event.getPrice())
                .status(ReservationStatus.ACTIVE)
                .start(event.getStart())
                .end(event.getEnd())
                .createdTime(Instant.now())
                .paymentId(UUID.randomUUID().toString())
                .build();
    }
}
