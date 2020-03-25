package com.journeyplanner.user.domain.user

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
class GetUserSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
    }

    def "should return first page with users"() {
        given:
        def user1 = UserMotherObject.aUser("aragorn@middleearth.com")
        def user2 = UserMotherObject.aUser("legolas@forest.com")
        def user3 = UserMotherObject.aUser("gimli@moria.com")

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        when:
        def result = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("\"pageNumber\":0")
        result.response.getContentAsString().contains("\"totalPages\":1")
        result.response.getContentAsString().contains("\"pageSize\":10")
        result.response.getContentAsString().contains("\"offset\":0")
        result.response.getContentAsString().concat("\"numberOfElements\":3")
    }

    def "should return first page with two users"() {
        given:
        def user1 = UserMotherObject.aUser("aragorn@middleearth.com")
        def user2 = UserMotherObject.aUser("legolas@forest.com")
        def user3 = UserMotherObject.aUser("gimli@moria.com")

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        when:
        def result = mvc.perform(get("/users/?page=0&size=2"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains("\"pageNumber\":0")
        result.response.getContentAsString().contains("\"totalPages\":2")
        result.response.getContentAsString().contains("\"pageSize\":2")
        result.response.getContentAsString().contains("\"offset\":0")
        result.response.getContentAsString().concat("\"numberOfElements\":2")
    }

    def "should get first page with email"() {
        given:
        def user1 = UserMotherObject.aUser("aragorn@middleearth.com")
        def user2 = UserMotherObject.aUser("legolas@forest.com")
        def user3 = UserMotherObject.aUser("gimli@moria.com")

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        when:
        def result = mvc.perform(get("/users/?email=aragorn"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(user1.email)
        !result.response.getContentAsString().contains(user2.email)
        !result.response.getContentAsString().contains(user3.email)
    }

    def "should get first page with status"() {
        given:
        def user1 = UserMotherObject.aUser("aragorn@middleearth.com", "USER", Boolean.TRUE)
        def user2 = UserMotherObject.aUser("legolas@forest.com")
        def user3 = UserMotherObject.aUser("gimli@moria.com")

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)

        when:
        def result = mvc.perform(get("/users/?isBlocked=false"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        !result.response.getContentAsString().contains(user1.email)
        result.response.getContentAsString().contains(user2.email)
        result.response.getContentAsString().contains(user3.email)
    }
}
