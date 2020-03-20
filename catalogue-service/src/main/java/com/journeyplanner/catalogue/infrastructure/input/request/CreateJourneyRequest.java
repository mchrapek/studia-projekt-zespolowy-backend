package com.journeyplanner.catalogue.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Value
@Builder
public class CreateJourneyRequest {

    @NotNull(message = "Name value must not be empty")
    @Size(min = 5, max = 80, message = "Name must be between 5 - 80 signs")
    String name;

    @NotNull(message = "Country value must not be empty")
    @Size(min = 5, max = 80, message = "Country must be between 5 - 80 signs")
    String country;

    @NotNull(message = "City value must not be empty")
    @Size(min = 5, max = 80, message = "City must be between 5 - 80 signs")
    String city;

    @NotNull(message = "Description value must not be empty")
    @Size(min = 5, max = 80, message = "Description must be between 5 - 5000 signs")
    String description;

    @NotNull(message = "Transport type value must not be empty")
    @Size(min = 3, max = 80, message = "Transport type must be between 5 - 80 signs")
    String transportType;

    @NotNull(message = "Price value must not be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price;

    @NotNull(message = "Start date must not be empty")
    @FutureOrPresent(message = "Start date must be not less than today")
    @DateTimeFormat( pattern="yyyy-MM-dd")
    Date start;

    @NotNull(message = "End date must not be empty")
    @FutureOrPresent(message = "End date must be not less than today")
    @DateTimeFormat( pattern="yyyy-MM-dd")
    Date end;
}
