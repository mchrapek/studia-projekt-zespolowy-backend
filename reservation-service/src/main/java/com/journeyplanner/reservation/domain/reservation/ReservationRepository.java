package com.journeyplanner.reservation.domain.reservation;

import org.springframework.data.repository.Repository;

interface ReservationRepository extends Repository<Reservation, String> {

    Reservation save(Reservation reservation);
}
