package com.journeyplanner.user.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
public class AddUserToBlacklistRequest {

    @NotNull(message = "Email value must not be empty")
    @Email(message = "Email should be valid")
    String email;
}
