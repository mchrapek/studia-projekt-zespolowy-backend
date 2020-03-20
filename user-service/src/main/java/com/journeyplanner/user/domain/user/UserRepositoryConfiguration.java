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
                .id(UUID.randomUUID().toString())
                .email("Todd@journeyplanner.com")
                .firstName("Todd")
                .secondName("Ray")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.ADMIN.getRoleName())
                .isBlocked(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(admin.getEmail()).isPresent()) {
            userRepository.save(admin);
        }

        User guide = User.builder()
                .id(UUID.randomUUID().toString())
                .email("maria@jourrapide.com")
                .firstName("Maria")
                .secondName("Kleins")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.GUIDE.getRoleName())
                .isBlocked(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(guide.getEmail()).isPresent()) {
            userRepository.save(guide);
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("BarbaraS@rhyta.com")
                .firstName("Barbara")
                .secondName("Siciliano")
                .password(passwordFacade.encodePassword("12345a"))
                .role(UserRole.USER.getRoleName())
                .isBlocked(Boolean.FALSE)
                .build();
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.save(user);
        }

        return args -> {
        };
    }
}
