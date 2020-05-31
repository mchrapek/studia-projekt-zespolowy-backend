package com.journeyplanner.catalogue.domain.journey

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.catalogue.app.CatalogueServiceApplication
import com.journeyplanner.catalogue.infrastructure.input.request.AddGuideToJourneyRequest
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
class AddGuideSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private JourneyRepository journeyRepository

    def setup() {
        journeyRepository.deleteAll()
    }

    def "should add guide to journey"() {
        given:
        def journey = JourneyMotherObject.aJourney()
        journeyRepository.save(journey)

        and:
        def request = new AddGuideToJourneyRequest("m@m.pl", "FirstName", "SecondName")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(put("/catalogue/journeys/" + journey.getId() + "/guides")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        journeyRepository.findById(journey.getId()).get().guideEmail == request.getEmail()
        journeyRepository.findById(journey.getId()).get().guideName == request.getFirstName() + " " + request.getSecondName()
    }


    def "should add empty guide to journey"() {
        given:
        def journey = JourneyMotherObject.aJourney()
        journeyRepository.save(journey)

        and:
        def request = new AddGuideToJourneyRequest("", "", "")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(put("/catalogue/journeys/" + journey.getId() + "/guides")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        journeyRepository.findById(journey.getId()).get().guideEmail == ""
        journeyRepository.findById(journey.getId()).get().guideName == ""
    }
}
