package com.journeyplanner.reservation.domain.reservation;

import org.springframework.data.repository.Repository;

import java.util.List;

interface ReservationRepository extends Repository<Reservation, String> {

    Reservation save(Reservation reservation);

    List<Reservation> getReservationByMail(String mail);

    void deleteAll();
}
