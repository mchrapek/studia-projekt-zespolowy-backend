package com.journeyplanner.reservation.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.journeyplanner.reservation.config",
        "com.journeyplanner.reservation.domain",
        "com.journeyplanner.reservation.infrastructure"
})
@EnableEurekaClient
@EnableMongoRepositories(basePackages = {
        "com.journeyplanner.reservation.domain"
})
@EnableScheduling
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }
}
