package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.rest.request.CreateJourneyRequest;

import java.util.UUID;

public class JourneyCreator {

    Journey from(CreateJourneyRequest request) {
        return Journey.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .city(request.getCity())
                .country(request.getName())
                .description(request.getDescription())
                .transportType(request.getTransportType())
                .price(request.getPrice())
                .start(request.getStart().toInstant())
                .end(request.getStart().toInstant())
                .build();
    }
}
