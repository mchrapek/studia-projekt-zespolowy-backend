package com.journeyplanner.catalogue.domain.journey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Configuration
public class JourneyRepositoryConfiguration {

    @Bean
    public CommandLineRunner init(JourneyRepository journeyRepository) {
        Journey journey = Journey.builder()
                .id("57ee7379-eaa9-4029-9267-dd0eb6a51b2b")
                .status(JourneyStatus.ACTIVE)
                .name("Beautiful Leipzig and Dresden")
                .country("Germany")
                .city("Leipzig and Dresden")
                .description("Day 1: Visiting beautiful Leipzig; Day 2: Visiting beautiful Dresden")
                .transportType("Train")
                .price(new BigDecimal(1000L))
                .start(Instant.now().plus(100, ChronoUnit.DAYS))
                .end(Instant.now().plus(102, ChronoUnit.DAYS))
                .guideEmail("")
                .guideName("")
                .build();
        if (!journeyRepository.findById(journey.getId()).isPresent()) {
            journeyRepository.save(journey);
        }

        return args -> {
        };
    }
}
