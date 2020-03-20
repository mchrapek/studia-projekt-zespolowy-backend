package com.journeyplanner.catalogue.infrastructure.output.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class ReservationCreator {

    private final String queueName;
    private final RabbitTemplate rabbitTemplate;

    public ReservationCreator(@Value("${queue.reservation.name}") String queueName, RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(CreateReservationEvent event) {
        try {
            String mappedEvent = new ObjectMapper().writeValueAsString(event);
            rabbitTemplate.convertAndSend(queueName, mappedEvent);
            log.info(format("Event send : {0} : {1}", queueName, mappedEvent));
        } catch (Exception e) {
            log.error(format("Cannot send message to : {0} : {1}", queueName, e.getMessage()));
        }
    }
}
