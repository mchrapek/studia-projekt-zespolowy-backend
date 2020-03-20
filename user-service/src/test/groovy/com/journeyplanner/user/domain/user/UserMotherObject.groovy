package com.journeyplanner.user.domain.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserMotherObject {

    static aUser(String email, String role = "USER", Boolean isBlocked = Boolean.FALSE) {
        User.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .firstName("FirstName")
                .secondName("SecondName")
                .password(new BCryptPasswordEncoder().encode("12345a"))
                .role(role)
                .isBlocked(isBlocked)
                .build()
    }
}
