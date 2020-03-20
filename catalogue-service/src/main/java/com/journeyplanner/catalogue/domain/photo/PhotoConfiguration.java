package com.journeyplanner.catalogue.domain.photo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhotoConfiguration {

    @Bean
    PhotoFacade photoFacade(PhotoRepository photoRepository) {
        return new PhotoFacade(photoRepository, new PhotoCreator());
    }
}
