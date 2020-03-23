package com.journeyplanner.reservation.domain.reservation

import java.time.Instant
import java.time.temporal.ChronoUnit

class ReservationMotherObject {

    static aReservation(String mail, Instant start = Instant.now().plus(100, ChronoUnit.DAYS), String journeyId = UUID.randomUUID().toString()) {
        Reservation.builder()
                .id(UUID.randomUUID().toString())
                .start(start)
                .end(Instant.now().plus(102, ChronoUnit.DAYS))
                .status(ReservationStatus.ACTIVE)
                .journeyId(journeyId)
                .mail(mail)
                .price(new BigDecimal(1000))
                .createdTime(Instant.now())
                .paymentId(UUID.randomUUID().toString())
                .build()
    }
}
