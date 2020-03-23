package com.journeyplanner.payment.domain.account

import com.journeyplanner.common.config.events.TransferType

import java.time.Instant

class TransferMotherObject {

    static aTransfer(String email, TransferType type, BigDecimal value = BigDecimal.TEN, eventTime = Instant.now()) {
        Transfer.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .paymentId(UUID.randomUUID().toString())
                .value(value)
                .type(type)
                .status(TransferStatus.PENDING)
                .eventTime(eventTime)
                .build()
    }
}
