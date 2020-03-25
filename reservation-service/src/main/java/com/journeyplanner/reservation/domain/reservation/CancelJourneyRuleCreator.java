package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.common.config.events.CancelJourneyEvent;

import java.time.Instant;
import java.util.UUID;

public class CancelJourneyRuleCreator {

    CancelJourneyRule from(final CancelJourneyEvent event) {
        return CancelJourneyRule.builder()
                .id(UUID.randomUUID().toString())
                .journeyId(event.getJourneyId())
                .createdTime(Instant.now())
                .status(CancelJourneyRuleStatus.ACTIVE)
                .build();
    }
}
