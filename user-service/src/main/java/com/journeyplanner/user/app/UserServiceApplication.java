package com.journeyplanner.user.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.journeyplanner.user.config",
        "com.journeyplanner.user.domain",
        "com.journeyplanner.user.infrastructure"
})
@EnableEurekaClient
@EnableMongoRepositories(basePackages = {
        "com.journeyplanner.user.domain"
})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
