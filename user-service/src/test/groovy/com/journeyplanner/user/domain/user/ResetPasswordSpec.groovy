package com.journeyplanner.user.domain.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.domain.password.ResetTokenRepository
import com.journeyplanner.user.domain.user.UserRepository
import com.journeyplanner.user.infrastructure.input.request.ResetPasswordRequest
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
class ResetPasswordSpec extends Specification {

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

    def "should reset user password"() {
        given:
        def email = "james@bond.com"
        def user = UserMotherObject.aUser(email)
        userRepository.save(user)

        and:
        def resetToken = ResetTokenMotherObject.aResetToken(email)
        resetTokenRepository.save(resetToken)

        and:
        def request = new ResetPasswordRequest(resetToken.getToken(), email, "78910a")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        userRepository.findByEmail(email).get().password != user.getPassword()
        !userRepository.findByEmail(email).get().newPasswordRequired
    }
}
