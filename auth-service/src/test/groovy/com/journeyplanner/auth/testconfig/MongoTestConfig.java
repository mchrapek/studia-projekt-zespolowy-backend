package com.journeyplanner.auth.testconfig;

import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@TestConfiguration
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.auth.user")
public class MongoTestConfig {
}
