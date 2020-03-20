package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.infrastructure.input.request.CreateUserRequest;

import java.util.UUID;

class UserCreator {

    User from(CreateUserRequest request, String encodedPassword) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .password(encodedPassword)
                .role(UserRole.USER.getRoleName())
                .isBlocked(Boolean.FALSE)
                .build();
    }
}
