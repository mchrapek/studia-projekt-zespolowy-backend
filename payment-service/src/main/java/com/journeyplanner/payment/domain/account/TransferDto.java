package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.TransferType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class TransferDto {

    String paymentId;
    String email;
    BigDecimal value;
    TransferType type;
    TransferStatus status;
    Instant eventTime;

    static TransferDto from(Transfer transfer) {
        return TransferDto.builder()
                .email(transfer.getEmail())
                .paymentId(transfer.getPaymentId())
                .value(transfer.getValue())
                .type(transfer.getType())
                .status(transfer.getStatus())
                .eventTime(transfer.getEventTime())
                .build();
    }
}
