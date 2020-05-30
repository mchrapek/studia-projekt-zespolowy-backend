package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.infrastructure.input.request.CreateGuideRequest;
import com.journeyplanner.user.infrastructure.input.request.CreateUserRequest;

import java.util.UUID;

class UserCreator {

    User from(final CreateUserRequest request, final String encodedPassword) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .password(encodedPassword)
                .role(UserRole.USER.getRoleName())
                .isBlocked(Boolean.FALSE)
                .newPasswordRequired(Boolean.FALSE)
                .build();
    }

    User from(final CreateGuideRequest request, final String encodedPassword) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .password(encodedPassword)
                .role(UserRole.GUIDE.getRoleName())
                .isBlocked(Boolean.FALSE)
                .newPasswordRequired(Boolean.FALSE)
                .build();
    }
}
