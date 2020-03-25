package com.journeyplanner.reservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor
public class CancelJourneyScheduler {

    private final CancelJourneyRuleRepository repository;
    private final ReservationFacade reservationFacade;

    @Scheduled(cron = "${reservation.cancel.cron}")
    public void cancel() {
        log.info(format("Start scheduled action : cancel journey rule : {0}", Instant.now()));
        List<CancelJourneyRule> rules = repository.findAllByStatus(CancelJourneyRuleStatus.ACTIVE);
        for (CancelJourneyRule rule : rules) {
            cancelAllReservationForJourney(rule.getJourneyId());
            checkShouldDeactivateRules(rule);
        }
        log.info("End scheduled action : cancel journey rule");
    }

    public void checkShouldDeactivateRules(final CancelJourneyRule rule) {
        if (Instant.now().plus(45, ChronoUnit.MINUTES).isBefore(rule.getCreatedTime())) {
            log.info(format("Deactivate rule for journey : {0}", rule.getJourneyId()));
            repository.updateCancelJourneyRuleStatusTo(rule.getId(), CancelJourneyRuleStatus.INACTIVE);
        }
    }

    public void cancelAllReservationForJourney(final String journeyId) {
        List<Reservation> reservations = reservationFacade.getActiveByJourney(journeyId);
        for (Reservation reservation : reservations) {
            reservationFacade.cancelByAdmin(reservation.getId(), reservation.getEmail());
            log.info(format("Reservation : {0} : canceled by rule journeyId : {1}", reservation.getEmail(), journeyId));
        }
    }
}
