package com.journeyplanner.catalogue.domain.journey;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Document(collection = "journey")
class Journey {

    @Id
    @NonNull String id;

    @NonNull String name;

    @NonNull String country;

    @NonNull String city;

    @NonNull String description;

    @NonNull String transportType;

    @NonNull BigDecimal price;

    @NonNull Instant start;

    @NonNull Instant end;
}
