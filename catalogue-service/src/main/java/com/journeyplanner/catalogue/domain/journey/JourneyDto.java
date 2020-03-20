package com.journeyplanner.catalogue.domain.journey;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class JourneyDto {

    String id;
    String name;
    String country;
    String city;
    String description;
    String transportType;
    BigDecimal price;
    Instant start;
    Instant end;

    static JourneyDto from(final Journey journey) {
        return JourneyDto.builder()
                .id(journey.getId())
                .name(journey.getName())
                .country(journey.getCountry())
                .city(journey.getCity())
                .description(journey.getDescription())
                .transportType(journey.getTransportType())
                .price(journey.getPrice())
                .start(journey.getStart())
                .end(journey.getEnd())
                .build();
    }
}
