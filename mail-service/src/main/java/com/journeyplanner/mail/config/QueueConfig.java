package com.journeyplanner.mail.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue mailQueue(@Value("${queue.mail.name}") String mailQueue) {
        return new Queue(mailQueue, true);
    }
}
