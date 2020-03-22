package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;

import java.util.UUID;

class JourneyCreator {

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
                .end(request.getEnd().toInstant())
                .build();
    }
}
