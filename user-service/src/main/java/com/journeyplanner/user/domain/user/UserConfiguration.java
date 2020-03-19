package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.domain.password.PasswordFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
class UserConfiguration {

    @Bean
    UserFacade userFacade(UserRepository userRepository, PasswordFacade passwordFacade) {
        return new UserFacade(userRepository, new UserCreator(), passwordFacade);
    }
}
