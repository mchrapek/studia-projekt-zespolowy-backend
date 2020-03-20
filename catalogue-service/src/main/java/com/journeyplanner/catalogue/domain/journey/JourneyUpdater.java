package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;

class JourneyUpdater {

    Journey from(UpdateJourneyRequest request) {
        return Journey.builder()
                .id(request.getId())
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
