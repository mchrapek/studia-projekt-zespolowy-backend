package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.input.request.AddGuideToJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;

class JourneyUpdater {

    Journey from(Journey journey, UpdateJourneyRequest request) {
        return Journey.builder()
                .id(journey.getId())
                .name(request.getName())
                .status(journey.getStatus())
                .city(request.getCity())
                .country(request.getName())
                .description(request.getDescription())
                .transportType(request.getTransportType())
                .price(request.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .guideName(journey.getGuideName())
                .guideEmail(journey.getGuideEmail())
                .build();
    }

    Journey from(Journey journey, AddGuideToJourneyRequest request) {
        return Journey.builder()
                .id(journey.getId())
                .name(journey.getName())
                .status(journey.getStatus())
                .city(journey.getCity())
                .country(journey.getCountry())
                .description(journey.getDescription())
                .transportType(journey.getTransportType())
                .price(journey.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .guideName(request.getFirstName() + " " + request.getSecondName())
                .guideEmail(request.getEmail())
                .build();
    }
}
