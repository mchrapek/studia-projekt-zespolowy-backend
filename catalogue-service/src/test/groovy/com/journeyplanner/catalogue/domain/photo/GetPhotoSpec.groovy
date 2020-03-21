package com.journeyplanner.catalogue.domain.photo

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
class GetPhotoSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private PhotoRepository photoRepository

    def setup() {
        photoRepository.deleteAll()
    }

    def "should get list of ids"() {
        given:
        def journeyId = UUID.randomUUID().toString()
        def photo = PhotoMotherObject.aPhoto(journeyId)
        photoRepository.save(photo)

        when:
        def result = mvc.perform(get("/journey/" + journeyId + "/photo"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(photo.getId())
    }

    def "should get photo"() {
        given:
        def journeyId = UUID.randomUUID().toString()
        def content = "PHOTO"
        def photo = PhotoMotherObject.aPhoto(journeyId, content)
        photoRepository.save(photo)

        when:
        def result = mvc.perform(get("/photo/" + photo.getId()))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString() == content
    }
}
