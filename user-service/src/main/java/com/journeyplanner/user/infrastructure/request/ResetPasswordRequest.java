package com.journeyplanner.user.infrastructure.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class ResetPasswordRequest {

    @NotNull(message = "Token value must not be empty")
    String token;

    @NotNull(message = "Email value must not be empty")
    @Email(message = "Email should be valid")
    String email;

    @NotNull(message = "New password")
    @Size(min = 5, max = 20, message = "Password must be between 3 - 20 signs")
    String newPassword;
}
