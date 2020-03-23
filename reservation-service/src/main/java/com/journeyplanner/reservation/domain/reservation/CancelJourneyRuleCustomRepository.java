package com.journeyplanner.reservation.domain.reservation;

interface CancelJourneyRuleCustomRepository {

    void updateCancelJourneyRuleStatusTo(String id, CancelJourneyRuleStatus status);
}
