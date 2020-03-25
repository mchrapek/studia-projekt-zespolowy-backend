package com.journeyplanner.catalogue.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class AddGuideToJourneyRequest {

    @NotNull(message = "Email value must not be empty")
    @Size(min = 5, max = 80, message = "Email must be between 5 - 80 signs")
    String email;

    @NotNull(message = "First name value must not be empty")
    @Size(min = 5, max = 80, message = "First name must be between 5 - 80 signs")
    String firstName;

    @NotNull(message = "Second name value must not be empty")
    @Size(min = 5, max = 80, message = "Second name must be between 5 - 80 signs")
    String secondName;
}
