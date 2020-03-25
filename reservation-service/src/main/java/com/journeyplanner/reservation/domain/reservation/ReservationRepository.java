package com.journeyplanner.reservation.domain.reservation;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ReservationRepository extends Repository<Reservation, String>, CustomReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(String id);

    Optional<Reservation> findByIdAndEmail(String id, String mail);

    List<Reservation> getReservationByJourneyIdAndStatus(String journeyId, ReservationStatus status);

    List<Reservation> getReservationByEmail(String mail);

    void deleteAll();
}
