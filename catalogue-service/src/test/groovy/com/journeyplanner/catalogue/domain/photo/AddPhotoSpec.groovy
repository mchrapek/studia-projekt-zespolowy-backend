package com.journeyplanner.catalogue.domain.photo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class AddPhotoSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private PhotoRepository photoRepository

    def setup() {
        photoRepository.deleteAll()
    }

    def "should add photo"() {
        given:
        def journeyId = UUID.randomUUID().toString()
        def request = new MockMultipartFile("image", "AVATAR".getBytes())

        when:
        mvc.perform(multipart("/journey/" + journeyId + "/photo")
                .file(request))
                .andExpect(status().isOk())
                .andReturn()

        then:
        photoRepository.findAllByJourneyId(journeyId).size() == 1
    }

    def "should add photos"() {
        given:
        def journeyId = UUID.randomUUID().toString()
        def request = new MockMultipartFile("image", "AVATAR".getBytes())

        when:
        mvc.perform(multipart("/journey/" + journeyId + "/photo")
                .file(request))
                .andExpect(status().isOk())
                .andReturn()

        and:
        mvc.perform(multipart("/journey/" + journeyId + "/photo")
                .file(request))
                .andExpect(status().isOk())
                .andReturn()

        then:
        photoRepository.findAllByJourneyId(journeyId).size() == 2
    }

}
