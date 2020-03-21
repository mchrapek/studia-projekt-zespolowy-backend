package com.journeyplanner.user.domain.details;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDetailsDto {

    String country;

    String city;

    String street;

    String postCode;

    String phoneNumber;

    static UserDetailsDto from(UserDetails userDetails) {
        return UserDetailsDto.builder()
                .country(userDetails.getCountry())
                .city(userDetails.getCity())
                .street(userDetails.getStreet())
                .postCode(userDetails.getPostCode())
                .phoneNumber(userDetails.getPhoneNumber())
                .build();
    }

    static UserDetailsDto empty() {
        return UserDetailsDto.builder().build();
    }
}
