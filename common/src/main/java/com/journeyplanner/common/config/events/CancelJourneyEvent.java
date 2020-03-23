package com.journeyplanner.common.config.events;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CancelJourneyEvent implements Event {

    @NonNull String id;

    @NonNull String journeyId;

    @NonNull Instant eventTimeStamp;
}
