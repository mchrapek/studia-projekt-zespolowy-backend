package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.domain.password.PasswordFacade;
import com.journeyplanner.user.infrastructure.output.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {

    @Bean
    UserFacade userFacade(UserRepository userRepository, MailSender mailSender, PasswordFacade passwordFacade) {
        return new UserFacade(userRepository, new UserCreator(), mailSender, passwordFacade);
    }
}
