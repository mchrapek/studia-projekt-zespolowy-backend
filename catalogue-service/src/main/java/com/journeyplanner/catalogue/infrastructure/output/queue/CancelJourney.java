package com.journeyplanner.catalogue.infrastructure.output.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.journeyplanner.common.config.events.Event;
import com.journeyplanner.common.config.events.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class CancelJourney implements EventPublisher {

    private final String queueName;
    private final RabbitTemplate rabbitTemplate;

    public CancelJourney(@Value("${queue.cancel-journey.name}") String queueName, RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Event event) {
        try {
            String mappedEvent = new ObjectMapper().findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(event);
            rabbitTemplate.convertAndSend(queueName, mappedEvent);
            log.info(format("Event send : {0} : {1}", queueName, mappedEvent));
        } catch (Exception e) {
            log.error(format("Cannot send message to : {0} : {1}", queueName, e.getMessage()));
        }
    }
}
