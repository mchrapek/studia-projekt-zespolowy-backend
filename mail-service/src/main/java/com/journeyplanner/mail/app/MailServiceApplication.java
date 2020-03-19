package com.journeyplanner.mail.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.journeyplanner.mail.domain",
        "com.journeyplanner.mail.infrastructure"
})
@EnableEurekaClient
@EnableMongoRepositories(basePackages = {
        "com.journeyplanner.mail.domain"
})
@EnableScheduling
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }
}
