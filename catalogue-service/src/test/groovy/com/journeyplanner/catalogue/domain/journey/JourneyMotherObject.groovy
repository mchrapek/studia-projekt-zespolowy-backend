package com.journeyplanner.catalogue.domain.journey

import java.time.Instant
import java.time.temporal.ChronoUnit

class JourneyMotherObject {

    static aJourney(String id = UUID.randomUUID().toString(), String name = "NameJourney") {
        Journey.builder()
                .id(id)
                .name(name)
                .country("Country")
                .status(JourneyStatus.ACTIVE)
                .city("City")
                .description("Description")
                .transportType("PLAIN")
                .price(new BigDecimal(1000))
                .start(Instant.now().plus(10, ChronoUnit.DAYS))
                .end(Instant.now().plus(12, ChronoUnit.DAYS))
                .guideName("")
                .guideEmail("")
                .build()
    }
}
