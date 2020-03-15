package com.journeyplanner.auth.user.model;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document(collection = "appuser")
public class AppUser {

    @Id
    String id;

    String email;

    String password;

    String role;
}
