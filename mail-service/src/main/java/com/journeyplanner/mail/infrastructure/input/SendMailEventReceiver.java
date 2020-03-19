package com.journeyplanner.mail.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.mail.domain.Mail;
import com.journeyplanner.mail.domain.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor
public class SendMailEventReceiver {

    private final MailService mailService;

    @RabbitListener(queues = "${queue.mail.name}")
    public void publish(String event) {
        try {
            SendMailEvent mailEvent = new ObjectMapper().readValue(event, SendMailEvent.class);
            log.info(format("Event received : {0}", mailEvent.toString()));
            mailService.createPendingMail(from(mailEvent));
        } catch (Exception e) {
            log.error(format("Malformed event received : {0}", event));
        }
    }

    private Mail from(SendMailEvent sendMailEvent) {
        return Mail.builder()
                .to(sendMailEvent.getTo())
                .params(sendMailEvent.getParams())
                .templateName(sendMailEvent.getTemplateName())
                .build();
    }
}
