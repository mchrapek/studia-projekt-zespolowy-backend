package com.journeyplanner.user.domain.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.infrastructure.input.request.CreateUserRequest
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
class CreateUserSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
    }

    def "should create user"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new CreateUserRequest(email, "12345a", "Aragorn", "Obiezyswiat")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        userRepository.findByEmail("aragorn@middleearth.com").isPresent()
    }

    def "should fail when adding user with the same mail"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new CreateUserRequest(email, "12345a", "Aragorn", "Obiezyswiat")
        def json = new ObjectMapper().writeValueAsString(request)

        when: "first attempt"
        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andReturn()

        and: "second attempt"
        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        userRepository.findByEmail(email).isPresent()
    }
}
