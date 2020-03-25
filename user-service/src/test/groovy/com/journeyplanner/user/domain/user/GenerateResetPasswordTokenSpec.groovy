package com.journeyplanner.user.domain.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.domain.password.ResetTokenRepository
import com.journeyplanner.user.domain.user.UserRepository
import com.journeyplanner.user.infrastructure.input.request.GenerateResetPasswordLinkRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class GenerateResetPasswordTokenSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ResetTokenRepository resetTokenRepository

    @Autowired
    private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
        resetTokenRepository.deleteAll()
    }

    def "should generate reset token password"() {
        given:
        def email = "aragorn@middleearth.com"
        def user = UserMotherObject.aUser(email)
        userRepository.save(user)

        and:
        def request = new GenerateResetPasswordLinkRequest(email)
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        resetTokenRepository.findByEmail(email).size() == 1
    }

    def "should fail when generating token for non exists user"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new GenerateResetPasswordLinkRequest(email)
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        resetTokenRepository.findByEmail(email).size() == 0
    }
}
