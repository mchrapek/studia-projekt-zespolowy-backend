package com.journeyplanner.auth.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentialsRequest {

    String username;
    String password;
}
