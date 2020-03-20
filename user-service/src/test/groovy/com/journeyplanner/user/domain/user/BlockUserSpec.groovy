package com.journeyplanner.user.domain.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.infrastructure.input.request.AddUserToBlacklistRequest
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
class BlockUserSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
    }

    def "should block exists user"() {
        given:
        def email = "james@bond.com"
        def user = UserMotherObject.aUser(email)
        userRepository.save(user)

        and:
        def request = new AddUserToBlacklistRequest(email)
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/block")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        userRepository.findByEmail(email).get().blocked
    }

    def "should fail when blocking non exists user"() {
        given:
        def email = "james@bond.com"

        and:
        def request = new AddUserToBlacklistRequest(email)
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/block")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        !userRepository.existsByEmail(email)
    }
}
