package com.journeyplanner.catalogue.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class AddGuideToJourneyRequest {

    @NotNull(message = "Email value must not be empty")
    String email;

    @NotNull(message = "First name value must not be empty")
    String firstName;

    @NotNull(message = "Second name value must not be empty")
    String secondName;
}
