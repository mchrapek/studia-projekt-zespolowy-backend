package com.journeyplanner.reservation.domain.reservation;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Value
@Builder
@Document(collection = "cancelJourney")
class CancelJourneyRule {

    @Id
    @NonNull String id;

    @Indexed(unique = true)
    @NonNull String journeyId;

    @NonNull Instant createdTime;

    @NonNull CancelJourneyRuleStatus status;
}

enum CancelJourneyRuleStatus {

    ACTIVE, INACTIVE;
}