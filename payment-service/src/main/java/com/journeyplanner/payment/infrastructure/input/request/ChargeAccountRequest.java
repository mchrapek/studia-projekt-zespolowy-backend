package com.journeyplanner.payment.infrastructure.input.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
public class ChargeAccountRequest {

    @NotNull(message = "Price value must not be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal value;
}
