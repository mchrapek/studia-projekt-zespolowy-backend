package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;

class JourneyUpdater {

    Journey from(Journey journey, UpdateJourneyRequest request) {
        return Journey.builder()
                .id(journey.getId())
                .name(request.getName())
                .city(request.getCity())
                .country(request.getName())
                .description(request.getDescription())
                .transportType(request.getTransportType())
                .price(request.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .build();
    }
}
