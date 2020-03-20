package com.journeyplanner.auth

import com.journeyplanner.auth.app.AuthServiceApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource

@SpringBootApplication
@ComponentScan(
        basePackages = [
                "com.journeyplanner.auth",
                "com.journeyplanner.common.config.security"
        ], excludeFilters = [
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuthServiceApplication.class)
        ]
)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.auth")
class ApplicationTestConfig {
}
