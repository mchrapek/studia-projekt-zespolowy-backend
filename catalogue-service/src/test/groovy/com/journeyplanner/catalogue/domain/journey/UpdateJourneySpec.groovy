package com.journeyplanner.catalogue.domain.journey

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class UpdateJourneySpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private JourneyRepository journeyRepository

    def setup() {
        journeyRepository.deleteAll()
    }

    def "should update journey"() {
        given:
        Journey journey = JourneyMotherObject.aJourney()
        journeyRepository.save(journey)

        and:
        def request = new UpdateJourneyRequest(journey.getId(), "NewName", journey.getCountry(),
                journey.getCity(), journey.getDescription(), journey.getTransportType(), journey.getPrice(),
                new Date(System.currentTimeMillis() + 1200 * 1000), new Date(System.currentTimeMillis() + 3600 * 1000))
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        journeyRepository.findById(journey.getId()).get().getName() == request.getName()
    }
}
