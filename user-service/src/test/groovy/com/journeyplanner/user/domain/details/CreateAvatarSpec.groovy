package com.journeyplanner.user.domain.details

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.domain.avatar.AvatarRepository
import com.journeyplanner.user.domain.user.UserRepository
import com.journeyplanner.user.infrastructure.input.request.UpdateUserDetailsRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CreateAvatarSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AvatarRepository avatarRepository

    def setup() {
        avatarRepository.deleteAll()
    }

    def "should create avatar"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new MockMultipartFile("image", "AVATAR".getBytes())

        when:
        mvc.perform(multipart("/avatar")
                .file(request)
                .header("x-username", email))
                .andExpect(status().isCreated())
                .andReturn()

        then:
        avatarRepository.findByMail(email).get().image.data.length != 0
    }

    def "should update avatar"() {
        given:
        def email = "aragorn@middleearth.com"
        def avatar = AvatarMotherObject.aAvatar(email)
        avatarRepository.save(avatar)

        and:
        def request = new MockMultipartFile("image", "S".getBytes())

        when:
        mvc.perform(multipart("/avatar")
                .file(request)
                .header("x-username", email))
                .andExpect(status().isCreated())
                .andReturn()

        then:
        avatarRepository.findByMail(email).get().image.data.length != 0
        avatarRepository.findByMail(email).get().image.data == [83]
    }
}
