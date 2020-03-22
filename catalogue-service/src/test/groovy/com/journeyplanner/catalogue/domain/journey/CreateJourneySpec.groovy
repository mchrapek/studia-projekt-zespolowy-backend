package com.journeyplanner.catalogue.domain.journey

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.catalogue.domain.journey.JourneyRepository
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType
import java.time.Instant
import java.time.temporal.ChronoUnit

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CreateJourneySpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private JourneyRepository journeyRepository

    def setup() {
        journeyRepository.deleteAll()
    }

    def "should create journey"() {
        given:
        def request = new CreateJourneyRequest("Name Journey", "Country", "CityCity", "Description", "PLAIN",
                new BigDecimal(1000), new Date(1614556800000), new Date(1614729600000))
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        def result = mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("2021-03-01")
        result.response.getContentAsString().contains("2021-03-03")
    }
}
