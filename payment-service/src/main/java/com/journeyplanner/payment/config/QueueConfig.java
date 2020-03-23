package com.journeyplanner.payment.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue paymentQueue(@Value("${queue.payment.name}") String paymentQueue) {
        return new Queue(paymentQueue, true);
    }
}
