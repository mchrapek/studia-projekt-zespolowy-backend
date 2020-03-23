package com.journeyplanner.reservation.domain.reservation;

import org.springframework.data.repository.Repository;

import java.util.List;

interface CancelJourneyRuleRepository extends Repository<CancelJourneyRule, String>, CancelJourneyRuleCustomRepository {

    CancelJourneyRule save(CancelJourneyRule cancelJourney);

    List<CancelJourneyRule> findAllByStatus(CancelJourneyRuleStatus status);

    void deleteAll();
}
