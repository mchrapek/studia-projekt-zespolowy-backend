package com.journeyplanner.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtPropertiesProvider {

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }
}
