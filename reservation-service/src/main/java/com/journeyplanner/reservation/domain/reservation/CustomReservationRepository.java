package com.journeyplanner.reservation.domain.reservation;

interface CustomReservationRepository {

    void updateReservationStatusTo(String id, ReservationStatus status);
}
