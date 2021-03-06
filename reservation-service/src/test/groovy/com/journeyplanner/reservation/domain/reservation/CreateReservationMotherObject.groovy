package com.journeyplanner.reservation.domain.reservation

import com.journeyplanner.common.config.events.CreateReservationEvent

import java.time.Instant
import java.time.temporal.ChronoUnit

class CreateReservationMotherObject {

    static aCreateReservation(String email, String journeyId = UUID.randomUUID().toString()) {
        CreateReservationEvent.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .journeyId(journeyId)
                .journeyName("JOURNEY_NAME")
                .price(new BigDecimal(1000))
                .eventTimeStamp(Instant.now())
                .start(Instant.now().plus(2, ChronoUnit.DAYS))
                .end(Instant.now().plus(2, ChronoUnit.DAYS))
                .build()
    }
}
