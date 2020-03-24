package com.journeyplanner.catalogue.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.journeyplanner.catalogue.config",
        "com.journeyplanner.catalogue.domain",
        "com.journeyplanner.catalogue.infrastructure"
})
@EnableEurekaClient
@EnableMongoRepositories(basePackages = {
        "com.journeyplanner.catalogue.domain"
})
public class CatalogueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogueServiceApplication.class, args);
    }
}
