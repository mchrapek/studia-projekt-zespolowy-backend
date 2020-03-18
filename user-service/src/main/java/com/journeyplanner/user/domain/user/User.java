package com.journeyplanner.user.domain.user;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(collection = "appUser")
class User {

    @Id
    @NonNull
    String id;

    @Indexed(unique = true)
    @NonNull
    String email;

    @NonNull
    String password;

    @NonNull
    String firstName;

    @NonNull
    String secondName;

    @NonNull
    String role;

    @NonNull
    boolean isBlocked;
}
