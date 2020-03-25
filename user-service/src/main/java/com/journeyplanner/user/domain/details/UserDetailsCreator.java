package com.journeyplanner.user.domain.details;

import com.journeyplanner.user.infrastructure.input.request.UpdateUserDetailsRequest;

import java.util.UUID;

class UserDetailsCreator {

    public UserDetails createFrom(final UserDetails userDetails, final UpdateUserDetailsRequest request) {
        return UserDetails.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .postCode(request.getPostCode())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public UserDetails createFrom(final String email, final UpdateUserDetailsRequest request) {
        return UserDetails.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .postCode(request.getPostCode())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
