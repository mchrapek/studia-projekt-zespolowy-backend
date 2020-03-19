package com.journeyplanner.catalogue.domain.journey;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Value
@Document(collection = "journey")
class Journey {

    @Id
    @NonNull String id;

    @NonNull String name;

    @NonNull String description;

    @NonNull TransportType transportType;

    @NonNull BigInteger price;

    @NonNull Accommodation accommodation;

    @NonNull List<JourneyPlace> places;

    @NonNull Instant start;

    @NonNull Instant end;
}

@Value
class JourneyPlace {

    @NonNull String name;

    @NonNull String country;

    @NonNull String description;
}

enum TransportType {

    BUS, PLAIN, TRAIN, OWN;
}