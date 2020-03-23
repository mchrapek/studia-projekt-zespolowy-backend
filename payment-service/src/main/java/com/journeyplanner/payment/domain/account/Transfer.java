package com.journeyplanner.payment.domain.account;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(collection = "payment")
public class Payment {
}
