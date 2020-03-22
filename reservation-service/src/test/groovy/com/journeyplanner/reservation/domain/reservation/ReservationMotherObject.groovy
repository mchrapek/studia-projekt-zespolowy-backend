package com.journeyplanner.reservation.domain.reservation

import java.time.Instant

class ReservationMotherObject {

    static aReservation(String mail) {
        Reservation.builder()
                .id(UUID.randomUUID().toString())
                .status(ReservationStatus.ACTIVE)
                .journeyId(UUID.randomUUID().toString())
                .mail(mail)
                .price(new BigDecimal(1000))
                .createdTime(Instant.now())
                .paymentId(UUID.randomUUID().toString())
                .build()
    }
}
