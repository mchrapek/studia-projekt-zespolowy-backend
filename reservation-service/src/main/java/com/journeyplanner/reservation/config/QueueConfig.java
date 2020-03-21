package com.journeyplanner.reservation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue reservationQueue(@Value("${queue.reservation.name}") String reservationQueue) {
        return new Queue(reservationQueue, true);
    }
}
