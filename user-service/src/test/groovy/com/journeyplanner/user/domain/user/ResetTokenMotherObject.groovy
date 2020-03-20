package com.journeyplanner.user.domain.user

import com.journeyplanner.user.domain.password.ResetToken

class ResetTokenMotherObject {

    static aResetToken(String email) {
        ResetToken.builder()
                .token(UUID.randomUUID().toString())
                .email(email)
                .active(Boolean.TRUE)
                .build()
    }
}
