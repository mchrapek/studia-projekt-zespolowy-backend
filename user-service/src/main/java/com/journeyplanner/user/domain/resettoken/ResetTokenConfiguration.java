package com.journeyplanner.user.domain.resettoken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResetTokenConfiguration {

    @Bean
    ResetTokenFacade resetTokenFacade(ResetTokenRepository resetTokenRepository) {
        return new ResetTokenFacade(resetTokenRepository, new ResetTokenCreator());
    }
}
