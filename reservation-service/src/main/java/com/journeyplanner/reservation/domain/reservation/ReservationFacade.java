package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CreateReservationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ReservationFacade {

    private final ReservationRepository repository;
    private final ReservationCreator creator;

    public void createNew(final CreateReservationEvent event) {
        Reservation reservation = creator.from(event);
        repository.save(reservation);
    }

    public List<ReservationDto> getUserReservation(final String mail) {
        return repository.getReservationByMail(mail)
                .stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }
}
