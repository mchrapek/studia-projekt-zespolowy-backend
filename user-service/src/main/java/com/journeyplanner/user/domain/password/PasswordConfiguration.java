package com.journeyplanner.user.domain.password;

import com.journeyplanner.user.infrastructure.output.queue.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class PasswordConfiguration {

    @Bean
    PasswordFacade resetTokenFacade(ResetTokenRepository resetTokenRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
        return new PasswordFacade(resetTokenRepository, new ResetTokenCreator(), mailSender, passwordEncoder);
    }
}
