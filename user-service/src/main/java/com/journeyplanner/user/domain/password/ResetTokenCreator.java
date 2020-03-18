package com.journeyplanner.user.domain.password;

import com.journeyplanner.user.infrastructure.request.GenerateResetPasswordLinkRequest;

import java.util.UUID;

class ResetTokenCreator {

    ResetToken from(final String email) {
        return ResetToken.builder()
                .token(UUID.randomUUID().toString())
                .email(email)
                .active(Boolean.TRUE)
                .build();
    }
}
