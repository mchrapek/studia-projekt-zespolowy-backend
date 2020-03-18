package com.journeyplanner.user.infrastructure.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class CreateUserRequest {

    @NotNull(message = "Email value must not be empty")
    @Email(message = "Email should be valid")
    String email;

    @NotNull(message = "Password value must not be empty")
    @Size(min = 5, max = 20, message = "Password must be between 3 - 20 signs")
    String password;

    @NotNull(message = "First name must not be empty")
    @Size(min = 3, max = 60, message = "First name must be between 3 - 20 signs")
    String firstName;

    @NotNull(message = "Second name must not be empty")
    @Size(min = 3, max = 60, message = "Second name must be between 3 - 20 signs")
    String secondName;
}
