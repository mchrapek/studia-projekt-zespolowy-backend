package com.journeyplanner.reservation.domain.reservation;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ReservationRepository extends Repository<Reservation, String>, CustomReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findByIdAndMail(String id, String mail);

    List<Reservation> getReservationByMail(String mail);

    void deleteAll();
}
