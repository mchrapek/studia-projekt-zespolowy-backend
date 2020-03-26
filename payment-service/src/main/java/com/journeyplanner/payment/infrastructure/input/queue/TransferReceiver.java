package com.journeyplanner.payment.infrastructure.input.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.journeyplanner.common.config.events.CreateTransferEvent;
import com.journeyplanner.payment.domain.account.AccountFacade;
import com.journeyplanner.payment.domain.account.Transfer;
import com.journeyplanner.payment.domain.account.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor
public class TransferReceiver {

    private final AccountFacade accountFacade;

    @RabbitListener(queues = "${queue.payment.name}")
    public void publish(String event) {
        try {
            CreateTransferEvent transferEvent = new ObjectMapper().findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).readValue(event, CreateTransferEvent.class);
            log.info(format("Event received : {0}", transferEvent.toString()));
            accountFacade.savePendingTransfer(from(transferEvent));
        } catch (Exception e) {
            log.error(format("Malformed event received : {0}", event));
        }
    }

    private Transfer from(CreateTransferEvent event) {
        return Transfer.builder()
                .id(event.getId())
                .email(event.getEmail())
                .paymentId(event.getPaymentId())
                .value(event.getValue())
                .type(event.getType())
                .status(TransferStatus.PENDING)
                .eventTime(event.getCreatedTime())
                .build();
    }
}
