package com.journeyplanner.user.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class RemoveUserFromBlacklistRequest {

    @NotNull(message = "Email value must not be empty")
    @Email(message = "Email should be valid")
    String email;
}
