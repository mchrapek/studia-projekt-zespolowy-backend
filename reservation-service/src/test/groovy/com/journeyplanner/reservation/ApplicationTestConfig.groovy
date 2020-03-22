package com.journeyplanner.reservation

import com.journeyplanner.reservation.app.ReservationServiceApplication
import com.journeyplanner.reservation.config.QueueConfig
import com.journeyplanner.reservation.infrastructure.input.ReservationCreateEventReceiver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource

@SpringBootApplication
@ComponentScan(
        basePackages = [
                "com.journeyplanner.reservation"
        ], excludeFilters = [
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = QueueConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ReservationCreateEventReceiver.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ReservationServiceApplication.class)
        ]
)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.reservation.domain")
class ApplicationTestConfig {
}
