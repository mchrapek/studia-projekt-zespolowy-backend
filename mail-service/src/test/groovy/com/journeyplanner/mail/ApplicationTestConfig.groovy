package com.journeyplanner.mail


import com.journeyplanner.mail.app.MailServiceApplication
import com.journeyplanner.mail.infrastructure.input.SendMailEventReceiver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource

@SpringBootApplication
@ComponentScan(basePackages = [
        "com.journeyplanner.mail"
], excludeFilters = [
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MailServiceApplication.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SendMailEventReceiver.class)
])
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.mail.domain")
class ApplicationTestConfig {
}
