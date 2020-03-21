package com.journeyplanner.user.domain.details;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(value = "userDetails")
class UserDetails {

    @Id
    @NonNull String id;

    @Indexed(unique = true)
    @NonNull String email;

    @NonNull String country;

    @NonNull String city;

    @NonNull String street;

    @NonNull String postCode;

    @NonNull String phoneNumber;
}
