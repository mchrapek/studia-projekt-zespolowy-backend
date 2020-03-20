package com.journeyplanner.catalogue.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class CreateReservationRequest {

    @NotNull(message = "Id value must not be empty")
    String id;
}
