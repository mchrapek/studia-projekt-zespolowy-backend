package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.*;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.reservation.exception.NotPermittedOperation;
import com.journeyplanner.reservation.exception.ResourceNotFound;
import com.journeyplanner.reservation.infrastructure.output.MailSender;
import com.journeyplanner.reservation.infrastructure.output.PaymentCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class ReservationFacade {

    private final ReservationRepository repository;
    private final ReservationCreator creator;
    private final CancelJourneyRuleCreator cancelJourneyRuleCreator;
    private final CancelJourneyRuleRepository cancelJourneyRuleRepository;
    private final MailSender mailSender;
    private final PaymentCreator paymentCreator;

    public void createNew(final CreateReservationEvent event) {
        Reservation reservation = creator.from(event);
        repository.save(reservation);

        paymentCreator.publish(CreateTransferEvent.builder()
                .id(UUID.randomUUID().toString())
                .email(event.getEmail())
                .paymentId(reservation.getPaymentId())
                .value(reservation.getPrice())
                .type(TransferType.LOAD)
                .createdTime(Instant.now())
                .build());

        mailSender.publish(SendMailEvent.builder().id(UUID.randomUUID().toString()).to(event.getEmail())
                .templateName(Template.NEW_RESERVATION_CREATED.getPath()).params(new HashMap<>()).build());

        log.info(format("New reservation created for journey : {0} : for user : {1}", event.getJourneyId(), event.getEmail()));
    }

    public List<ReservationDto> getUserReservation(final String email) {
        return repository.getReservationByEmail(email)
                .stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }

    public List<Reservation> getActiveByJourney(final String journeyId) {
        return repository.getReservationByJourneyIdAndStatus(journeyId, ReservationStatus.ACTIVE);
    }

    public void cancelByUser(final String email, final String reservationId) {
        Reservation reservation = repository.findByIdAndEmail(reservationId, email)
                .orElseThrow(() -> new ResourceNotFound(format("Cannot found reservation with id : {0}", reservationId)));

        if (!reservation.getEmail().equals(email)) {
            throw new NotPermittedOperation("You don't have permission");
        }

        if (Instant.now().plus(14, ChronoUnit.DAYS).isAfter(reservation.getStart())) {
            log.info(format("Cannot cancel reservation : {0} : 14 days before start for user : {1}", reservationId, email));
            throw new NotPermittedOperation(format("Cannot cancel reservation 14 days before start : {0}", reservationId));
        }

        repository.updateReservationStatusTo(reservation.getId(), ReservationStatus.CANCEL);

        paymentCreator.publish(CreateTransferEvent.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .paymentId(reservation.getPaymentId())
                .value(reservation.getPrice())
                .type(TransferType.RETURN)
                .createdTime(Instant.now())
                .build());

        mailSender.publish(SendMailEvent.builder().id(UUID.randomUUID().toString()).to(email)
                .templateName(Template.RESERVATION_CANCELED.getPath()).params(new HashMap<>()).build());
    }

    public void createNewCancelEvent(final CancelJourneyEvent cancelJourneyEvent) {
        cancelJourneyRuleRepository.save(cancelJourneyRuleCreator.from(cancelJourneyEvent));
    }

    public void cancelByAdmin(final Reservation reservation) {
        repository.updateReservationStatusTo(reservation.getId(), ReservationStatus.CANCEL);

        paymentCreator.publish(CreateTransferEvent.builder()
                .id(UUID.randomUUID().toString())
                .email(reservation.getEmail())
                .paymentId(reservation.getPaymentId())
                .value(reservation.getPrice())
                .type(TransferType.RETURN)
                .createdTime(Instant.now())
                .build());

        mailSender.publish(SendMailEvent.builder().id(UUID.randomUUID().toString()).to(reservation.getEmail())
                .templateName(Template.JOURNEY_CANCELED.getPath()).params(new HashMap<>()).build());
    }

    public List<String> getAllUserEmailsForJourney(String journeyId) {
        return repository.findByJourneyId(journeyId)
                .stream()
                .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                .map(Reservation::getEmail)
                .collect(Collectors.toList());
    }
}
