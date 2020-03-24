package com.journeyplanner.catalogue.domain.journey


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class GetJourneySpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private JourneyRepository journeyRepository

    def setup() {
        journeyRepository.deleteAll()
    }

    def "should get first page"() {
        given:
        def journey1 = JourneyMotherObject.aJourney()
        def journey2 = JourneyMotherObject.aJourney()
        def journey3 = JourneyMotherObject.aJourney()

        journeyRepository.save(journey1)
        journeyRepository.save(journey2)
        journeyRepository.save(journey3)

        when:
        def result = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("\"pageNumber\":0")
        result.response.getContentAsString().contains("\"totalPages\":1")
        result.response.getContentAsString().contains("\"pageSize\":10")
        result.response.getContentAsString().contains("\"offset\":0")
        result.response.getContentAsString().contains("\"numberOfElements\":3")
    }

    def "should get first page with active status"() {
        given:
        def journeys = prepareJourneys()
        journeys.each { value -> journeyRepository.save(value) }

        when:
        def result = mvc.perform(get("/?status=ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("Journey1")
        result.response.getContentAsString().contains("Journey2")
        !result.response.getContentAsString().contains("Journey3")
    }

    def "should get first page with specific price"() {
        given:
        def journeys = prepareJourneys()
        journeys.each { value -> journeyRepository.save(value) }

        when:
        def result = mvc.perform(get("/?price=20&price=200"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        !result.response.getContentAsString().contains("Journey1")
        result.response.getContentAsString().contains("Journey2")
        !result.response.getContentAsString().contains("Journey3")
    }

    def "should get first page with specific date and active"() {
        given:
        def journeys = prepareJourneys()
        journeys.each { value -> journeyRepository.save(value) }

        when:
        def result = mvc.perform(get("/?start=2020-03-01T00:00:00.000Z&status=ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("Journey1")
        result.response.getContentAsString().contains("Journey2")
        !result.response.getContentAsString().contains("Journey3")
    }

    def prepareJourneys() {
        def journey1 = Journey.builder()
                .id(UUID.randomUUID().toString())
                .name("Journey1")
                .country("CountryJourney1")
                .status(JourneyStatus.ACTIVE)
                .city("CityJourney1")
                .description("DescriptionJourney1")
                .transportType("PLAIN")
                .price(new BigDecimal(10))
                .start(Instant.now().plus(10, ChronoUnit.DAYS))
                .end(Instant.now().plus(12, ChronoUnit.DAYS))
                .build()

        def journey2 = Journey.builder()
                .id(UUID.randomUUID().toString())
                .name("Journey2")
                .country("CountryJourney2")
                .status(JourneyStatus.ACTIVE)
                .city("CityJourney2")
                .description("DescriptionJourney2")
                .transportType("TRAIN")
                .price(new BigDecimal(100))
                .start(Instant.now().plus(15, ChronoUnit.DAYS))
                .end(Instant.now().plus(16, ChronoUnit.DAYS))
                .build()

        def journey3 = Journey.builder()
                .id(UUID.randomUUID().toString())
                .name("Journey3")
                .country("CountryJourney3")
                .status(JourneyStatus.INACTIVE)
                .city("CityJourney3")
                .description("DescriptionJourney3")
                .transportType("TRAIN")
                .price(new BigDecimal(1000))
                .start(Instant.now().plus(20, ChronoUnit.DAYS))
                .end(Instant.now().plus(22, ChronoUnit.DAYS))
                .build()

        [journey1, journey2, journey3]
    }
}

