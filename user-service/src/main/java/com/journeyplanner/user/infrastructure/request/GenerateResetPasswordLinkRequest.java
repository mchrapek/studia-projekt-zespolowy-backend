package com.journeyplanner.user.infrastructure.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class GenerateResetPasswordLinkRequest {

    @NotNull(message = "Email value must not be empty")
    @Email(message = "Email should be valid")
    String email;
}
