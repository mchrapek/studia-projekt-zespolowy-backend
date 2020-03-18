package com.journeyplanner.auth.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.auth.security.dto.UserCredentialsRequest
import com.journeyplanner.auth.user.AppUserRepository
import com.journeyplanner.auth.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CreateTokenIT extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AppUserRepository repository

    @Autowired
    private PasswordEncoder encoder

    def "send correct credentials should return token"() {
        given:
        repository.save(new AppUser(UUID.randomUUID().toString(), "james@bond.pl",
                encoder.encode("12345"), "James", "Bond", "USER"))
        def userCredentials = new UserCredentialsRequest("james@bond.pl", "12345")
        def json = new ObjectMapper().writeValueAsString(userCredentials)

        when:
        def result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        then:
        !result.response.getHeader("Authorization").isEmpty()
    }

    def "send incorrect credentials should return 400 status code without token"() {
        given:
        repository.save(new AppUser(UUID.randomUUID().toString(), "james@bond.com",
                encoder.encode("12345"), "James", "Bond", "USER"))
        def userCredentials = new UserCredentialsRequest("no@exists.pl", "12345")
        def json = new ObjectMapper().writeValueAsString(userCredentials)

        when:
        def result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        result.response.getHeader("Authorization") == null
    }
}
