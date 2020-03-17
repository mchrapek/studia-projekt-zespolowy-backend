package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.infrastructure.request.CreateAppUserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

class UserCreator {

    private final BCryptPasswordEncoder encoder;

    public UserCreator() {
        this.encoder = new BCryptPasswordEncoder();
    }

    User from(CreateAppUserRequest request) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .password(encoder.encode(request.getPassword()))
                .role(UserRole.USER.getRoleName())
                .build();
    }
}
