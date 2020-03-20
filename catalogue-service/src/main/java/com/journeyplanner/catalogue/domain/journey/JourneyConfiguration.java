package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.infrastructure.output.queue.ReservationCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JourneyConfiguration {

    @Bean
    JourneyFacade journeyFacade(JourneyRepository journeyRepository, ReservationCreator reservationCreator) {
        return new JourneyFacade(journeyRepository, new JourneyCreator(), new JourneyUpdater(), reservationCreator);
    }
}
