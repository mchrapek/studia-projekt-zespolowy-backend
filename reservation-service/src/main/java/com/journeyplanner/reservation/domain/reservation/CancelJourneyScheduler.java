package com.journeyplanner.reservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CancelJourneyScheduler {

    private final CancelJourneyRuleRepository repository;
    private final ReservationFacade reservationFacade;

    @Scheduled(cron = "${reservation.cancel.cron}")
    public void cancel() {
        List<CancelJourneyRule> rules = repository.findAllByStatus(CancelJourneyRuleStatus.ACTIVE);
        for (CancelJourneyRule rule : rules) {
            cancelAllReservationForJourney(rule.getJourneyId());
            checkShouldDeactivateRules(rule);
        }
    }

    public void checkShouldDeactivateRules(CancelJourneyRule rule) {
        if (Instant.now().plus(45, ChronoUnit.MINUTES).isBefore(rule.getCreatedTime())) {
            repository.updateCancelJourneyRuleStatusTo(rule.getId(), CancelJourneyRuleStatus.INACTIVE);
        }
    }

    public void cancelAllReservationForJourney(String journeyId) {
        List<Reservation> reservations = reservationFacade.getActiveByJourney(journeyId);
        for (Reservation reservation : reservations) {
            reservationFacade.cancelByAdmin(reservation.getId(), reservation.getMail());
        }
    }
}
