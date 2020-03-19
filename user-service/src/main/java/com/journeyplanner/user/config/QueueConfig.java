package com.journeyplanner.user.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class QueueConfig {

    @Bean
    public Queue myQueue(@Value("${queue.mail.name}") String mailQueue) {
        return new Queue(mailQueue, true);
    }
}
