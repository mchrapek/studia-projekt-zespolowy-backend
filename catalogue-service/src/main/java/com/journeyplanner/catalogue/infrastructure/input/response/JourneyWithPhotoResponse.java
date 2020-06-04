package com.journeyplanner.catalogue.infrastructure.input.response;

import com.journeyplanner.catalogue.domain.journey.Journey;
import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Value
@Builder
public class JourneyWithPhotoResponse {

    String id;
    String name;
    String country;
    JourneyStatus status;
    String city;
    String description;
    String transportType;
    BigDecimal price;
    Instant start;
    Instant end;
    String guideEmail;
    String guideName;
    List<String> photoIds;

    public static JourneyWithPhotoResponse from(final JourneyDto journey, final List<String> photoIds) {
        return JourneyWithPhotoResponse.builder()
                .id(journey.getId())
                .status(journey.getStatus())
                .name(journey.getName())
                .country(journey.getCountry())
                .city(journey.getCity())
                .description(journey.getDescription())
                .transportType(journey.getTransportType())
                .price(journey.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .guideEmail(journey.getGuideEmail())
                .guideName(journey.getGuideName())
                .photoIds(photoIds)
                .build();
    }
}
