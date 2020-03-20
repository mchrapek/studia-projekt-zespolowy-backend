package com.journeyplanner.catalogue.domain.journey;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ",timezone = "UTC")
    Instant start;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ",timezone = "UTC")
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
