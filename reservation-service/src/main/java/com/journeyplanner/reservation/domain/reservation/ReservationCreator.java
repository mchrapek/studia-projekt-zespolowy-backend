package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CreateReservationEvent;

import java.util.UUID;

class ReservationCreator {

    Reservation from(CreateReservationEvent event) {
        return Reservation.builder()
                .id(UUID.randomUUID().toString())
                .start(event.getStart())
                .end(event.getEnd())
                .mail(event.getEmail())
                .journeyId(event.getJourneyId())
                .price(event.getPrice())
                .status(ReservationStatus.ACTIVE)
                .createdTime(event.getEventTimeStamp())
                .paymentId(UUID.randomUUID().toString())
                .build();
    }
}
