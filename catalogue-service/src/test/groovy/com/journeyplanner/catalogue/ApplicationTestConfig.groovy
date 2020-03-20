package com.journeyplanner.catalogue

import com.journeyplanner.catalogue.app.CatalogueServiceApplication
import com.journeyplanner.catalogue.config.QueueConfig
import com.journeyplanner.catalogue.infrastructure.output.queue.ReservationCreator
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
                "com.journeyplanner.catalogue",
                "com.journeyplanner.common.config.security"
        ], excludeFilters = [
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CatalogueServiceApplication.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = QueueConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ReservationCreator.class)
        ]
)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureDataMongo
@EnableMongoRepositories(basePackages = "com.journeyplanner.catalogue.domain")
class ApplicationTestConfig {

    @MockBean
    ReservationCreator reservationCreator
}
