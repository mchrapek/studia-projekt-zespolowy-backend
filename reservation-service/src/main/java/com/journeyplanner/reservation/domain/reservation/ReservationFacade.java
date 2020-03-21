package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CreateReservationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReservationFacade {

    private final ReservationRepository repository;
    private final ReservationCreator creator;

    public void createNew(CreateReservationEvent event) {
        creator.from(event);
    }
}
