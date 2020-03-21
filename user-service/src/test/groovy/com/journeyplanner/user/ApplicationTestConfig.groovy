package com.journeyplanner.user

import com.journeyplanner.user.app.UserServiceApplication
import com.journeyplanner.user.infrastructure.output.queue.MailSender
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource

@SpringBootApplication
@ComponentScan(
        basePackages = [
                "com.journeyplanner.user",
                "com.journeyplanner.common.config.security"
        ], excludeFilters = [
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MailSender.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserServiceApplication.class)
        ]
)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.user.domain")
class ApplicationTestConfig {

    @MockBean
    MailSender mailSender
}
