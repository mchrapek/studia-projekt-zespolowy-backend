package com.journeyplanner.catalogue.domain.journey

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CancelJourneySpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private JourneyRepository journeyRepository

    def setup() {
        journeyRepository.deleteAll()
    }

    def "should cancel journey"() {
        given:
        def journey = JourneyMotherObject.aJourney()
        journeyRepository.save(journey)

        when:
        mvc.perform(delete("/catalogue/journeys/" + journey.getId()))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        journeyRepository.findById(journey.getId()).get().status == JourneyStatus.INACTIVE
    }
}
