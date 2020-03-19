package com.journeyplanner.user.infrastructure.output.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journeyplanner.common.config.events.SendMailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class MailSender {

    private final String queueName;
    private final RabbitTemplate rabbitTemplate;

    public MailSender(@Value("${queue.mail.name}") String queueName, RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(SendMailEvent event) {
        try {
            String mappedEvent = new ObjectMapper().writeValueAsString(event);
            rabbitTemplate.convertAndSend(queueName, mappedEvent);
            log.info(format("Event send : {0} : {1}", queueName, mappedEvent));
        } catch (Exception e) {
            log.error(format("Cannot send message to : {0} : {1}", queueName, e.getMessage()));
        }
    }
}
