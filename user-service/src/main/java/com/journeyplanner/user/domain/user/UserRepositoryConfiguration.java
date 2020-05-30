package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.domain.password.PasswordFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class UserRepositoryConfiguration {

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordFacade passwordFacade) {
        User admin = User.builder()
                .id("c5eb23dc-b6d0-4485-b0a2-570523777313")
                .email("Todd@journeyplanner.com")
                .firstName("Todd")
                .secondName("Ray")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.ADMIN.getRoleName())
                .isBlocked(Boolean.FALSE)
                .newPasswordRequired(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(admin.getEmail()).isPresent()) {
            userRepository.save(admin);
        }

        User guide = User.builder()
                .id("fd1f2ec4-21d1-4c32-a692-eade9fd19cfe")
                .email("maria@jourrapide.com")
                .firstName("Maria")
                .secondName("Kleins")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.GUIDE.getRoleName())
                .isBlocked(Boolean.FALSE)
                .newPasswordRequired(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(guide.getEmail()).isPresent()) {
            userRepository.save(guide);
        }

        User user = User.builder()
                .id("380d1dad-bc3f-4cbb-9b12-7138afa170b2")
                .email("BarbaraS@rhyta.com")
                .firstName("Barbara")
                .secondName("Siciliano")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.USER.getRoleName())
                .isBlocked(Boolean.FALSE)
                .newPasswordRequired(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.save(user);
        }

        return args -> {
        };
    }
}
