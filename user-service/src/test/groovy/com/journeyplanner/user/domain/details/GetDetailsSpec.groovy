package com.journeyplanner.user.domain.details


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
class GetDetailsSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserDetailsRepository userDetailsRepository

    def setup() {
        userDetailsRepository.deleteAll()
    }

    def "should return empty user details"() {
        given:
        def email = "aragorn@middleearth.com"

        when:
        def result = mvc.perform(get("/users/details")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("\"country\":null")
    }

    def "should return user details by email"() {
        given:
        def email = "aragorn@middleearth.com"
        def userDetails = UserDetailsMotherObject.aUserDetails(email)
        userDetailsRepository.save(userDetails)

        when:
        def result = mvc.perform(get("/users/details")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(userDetails.getCity())
        result.response.getContentAsString().contains(userDetails.getPhoneNumber())
    }

    def "should return user details by id"() {
        given:
        def email = "aragorn@middleearth.com"
        def userDetails = UserDetailsMotherObject.aUserDetails(email)
        userDetailsRepository.save(userDetails)

        when:
        def result = mvc.perform(get("/users/details/" + userDetails.getId()))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(userDetails.getCity())
        result.response.getContentAsString().contains(userDetails.getPhoneNumber())
    }
}
