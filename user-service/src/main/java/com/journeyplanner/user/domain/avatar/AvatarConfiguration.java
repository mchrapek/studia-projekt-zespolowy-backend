package com.journeyplanner.user.domain.avatar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvatarConfiguration {

    @Bean
    AvatarFacade avatarFacade(AvatarRepository avatarRepository) {
        return new AvatarFacade(avatarRepository, new AvatarCreator());
    }
}
