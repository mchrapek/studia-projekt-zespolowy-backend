package com.journeyplanner.user.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class UpdateUserDetailsRequest {

    @NotNull(message = "Country value must not be empty")
    @Size(min = 3, max = 40, message = "Country must be between 3 - 40 signs")
    String country;

    @NotNull(message = "City value must not be empty")
    @Size(min = 3, max = 40, message = "City must be between 3 - 40 signs")
    String city;

    @NotNull(message = "Street value must not be empty")
    @Size(min = 3, max = 40, message = "Street must be between 3 - 40 signs")
    String street;

    @NotNull(message = "PostCode value must not be empty")
    @Size(min = 3, max = 40, message = "PostCode must be between 3 - 40 signs")
    String postCode;

    @NotNull(message = "PhoneNumber value must not be empty")
    @Size(min = 3, max = 40, message = "PhoneNumber must be between 3 - 40 signs")
    String phoneNumber;
}
