package com.journeyplanner.user.domain.resettoken;

import com.journeyplanner.user.infrastructure.request.ResetPasswordLinkRequest;

import java.util.UUID;

class ResetTokenCreator {

    ResetToken from(ResetPasswordLinkRequest request) {
        return ResetToken.builder()
                .token(UUID.randomUUID().toString())
                .email(request.getEmail())
                .active(Boolean.TRUE)
                .build();
    }
}
