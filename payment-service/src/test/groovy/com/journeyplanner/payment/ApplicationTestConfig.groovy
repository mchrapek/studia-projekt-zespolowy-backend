package com.journeyplanner.payment

import com.journeyplanner.payment.app.PaymentServiceApplication
import com.journeyplanner.payment.config.QueueConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource

@SpringBootApplication
@ComponentScan(
        basePackages = [
                "com.journeyplanner.payment"
        ], excludeFilters = [
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = QueueConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = PaymentServiceApplication.class)
        ]
)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.payment.domain")
class ApplicationTestConfig {
}
