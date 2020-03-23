package com.journeyplanner.reservation.domain.reservation

import java.time.Instant

class CancelJourneyRuleMotherObject {

    static aRule(String journeyId, Instant createdTime = Instant.now()) {
        CancelJourneyRule.builder()
                .id(UUID.randomUUID().toString())
                .journeyId(journeyId)
                .status(CancelJourneyRuleStatus.ACTIVE)
                .createdTime(createdTime)
                .build()
    }
}
