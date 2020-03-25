package com.journeyplanner.user.domain.details

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.user.infrastructure.input.request.UpdateUserDetailsRequest
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
class CreateDetailsSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserDetailsRepository userDetailsRepository

    def setup() {
        userDetailsRepository.deleteAll()
    }

    def "should create user details"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new UpdateUserDetailsRequest("Country", "City", "Street", "00-000", "0001112222")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-username", email)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        userDetailsRepository.findByEmail(email).get().email == email
    }

    def "should fail when creating details for non logged user"() {
        given:
        def email = "aragorn@middleearth.com"
        def request = new UpdateUserDetailsRequest("Country", "City", "Street", "00-000", "0001112222")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        !userDetailsRepository.findByEmail(email).isPresent()
    }

    def "should update user details"() {
        given:
        def email = "aragorn@middleearth.com"
        def userDetails = UserDetailsMotherObject.aUserDetails(email)
        userDetailsRepository.save(userDetails)

        and:
        def request = new UpdateUserDetailsRequest("Country", "City", "Street", "00-000", "0001112222")
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/users/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-username", email)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        userDetailsRepository.findByEmail(email).get().email == email
        userDetailsRepository.findByEmail(email).get().phoneNumber == request.getPhoneNumber()
    }
}
