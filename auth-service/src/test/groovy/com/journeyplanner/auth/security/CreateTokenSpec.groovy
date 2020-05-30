package com.journeyplanner.auth.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.auth.security.dto.UserCredentialsRequest
import com.journeyplanner.auth.user.AppUserRepository
import com.journeyplanner.auth.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CreateTokenSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AppUserRepository repository

    @Autowired
    private PasswordEncoder encoder

    def setup() {
        repository.deleteAll()
    }

    def "should return token"() {
        given:
        def email = "james@bond.com"
        repository.save(new AppUser(UUID.randomUUID().toString(), email,
                encoder.encode("12345"), "James", "Bond", "USER", Boolean.FALSE, Boolean.FALSE))

        and:
        def userCredentials = new UserCredentialsRequest(email, "12345")
        def json = new ObjectMapper().writeValueAsString(userCredentials)

        when:
        def result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        print(result.response.getContentAsString())
        result.response.getContentAsString().contains("Bearer")
    }

    def "should return 400 status code without token when credentials are not valid"() {
        given:
        repository.save(new AppUser(UUID.randomUUID().toString(), "james@bond.com",
                encoder.encode("12345"), "James", "Bond", "USER", Boolean.FALSE, Boolean.FALSE))

        and:
        def userCredentials = new UserCredentialsRequest("no@exists.pl", "12345")
        def json = new ObjectMapper().writeValueAsString(userCredentials)

        when:
        def result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        !result.response.getContentAsString().contains("Bearer")
    }

    def "should return 400 status without token when user is blocked"() {
        given:
        repository.save(new AppUser(UUID.randomUUID().toString(), "gandalf@white.com",
                encoder.encode("12345"), "Gandalf", "White", "USER", Boolean.TRUE, Boolean.FALSE))

        and:
        def userCredentials = new UserCredentialsRequest("gandalf@white.com", "12345")
        def json = new ObjectMapper().writeValueAsString(userCredentials)

        when:
        def result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        !result.response.getContentAsString().contains("Bearer")
    }
}
