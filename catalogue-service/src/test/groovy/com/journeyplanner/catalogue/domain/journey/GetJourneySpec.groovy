package com.journeyplanner.catalogue.domain.journey

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

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
        result.response.getContentAsString().concat("\"numberOfElements\":3")
    }
}

