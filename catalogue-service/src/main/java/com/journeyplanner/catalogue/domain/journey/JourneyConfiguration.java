package com.journeyplanner.catalogue.domain.journey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JourneyConfiguration {

    @Bean
    JourneyFacade journeyFacade(JourneyRepository journeyRepository) {
        return new JourneyFacade(journeyRepository);
    }
}
