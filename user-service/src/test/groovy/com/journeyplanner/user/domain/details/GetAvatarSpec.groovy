package com.journeyplanner.user.domain.details

import com.journeyplanner.user.domain.avatar.AvatarRepository
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
class GetAvatarSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AvatarRepository avatarRepository

    def setup() {
        avatarRepository.deleteAll()
    }

    def "should get avatar"() {
        given:
        def email = "aragorn@middleearth.com"
        def avatar = AvatarMotherObject.aAvatar(email)
        avatarRepository.save(avatar)

        when:
        def result = mvc.perform(get("/users/avatar")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("AVATAR")
    }

    def "should fail when avatar doesn't exist"() {
        given:
        def email = "aragorn@middleearth.com"

        when:
        def result = mvc.perform(get("/users/avatar")
                .header("x-username", email))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        println result.response.getContentAsString()
    }
}
