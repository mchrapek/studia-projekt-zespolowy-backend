package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CancelJourneyEvent;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import com.journeyplanner.reservation.exception.NotPermittedOperation;
import com.journeyplanner.reservation.exception.ResourceNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class ReservationFacade {

    private final ReservationRepository repository;
    private final ReservationCreator creator;
    private final CancelJourneyRuleCreator cancelJourneyRuleCreator;
    private final CancelJourneyRuleRepository cancelJourneyRuleRepository;

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

    public List<Reservation> getActiveByJourney(String journeyId) {
        return repository.getReservationByJourneyIdAndStatus(journeyId, ReservationStatus.ACTIVE);
    }

    public void cancelByUser(final String mail, final String reservationId) {
        Reservation reservation = repository.findByIdAndMail(reservationId, mail)
                .orElseThrow(() -> new ResourceNotFound(format("Cannot found journey with id : {0}", reservationId)));

        if (Instant.now().plus(14, ChronoUnit.DAYS).isAfter(reservation.getStart())) {
            throw new NotPermittedOperation(format("Cannot cancel reservation 14 days before start {0}", reservationId));
        }

        repository.updateReservationStatusTo(reservation.getId(), ReservationStatus.CANCEL);
    }

    public void createNewCancelEvent(CancelJourneyEvent cancelJourneyEvent) {
        cancelJourneyRuleRepository.save(cancelJourneyRuleCreator.from(cancelJourneyEvent));
    }

    public void cancelByAdmin(final String reservationId) {
        repository.updateReservationStatusTo(reservationId, ReservationStatus.CANCEL);
    }
}
