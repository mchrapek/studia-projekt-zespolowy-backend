package com.journeyplanner.auth.security.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentialsRequest {

    String username;
    String password;
}
