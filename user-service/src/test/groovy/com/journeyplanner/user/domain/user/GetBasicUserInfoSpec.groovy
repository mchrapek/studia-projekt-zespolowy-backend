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
class GetBasicUserInfoSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
    }

    def "should return user basic info"() {
        given:
        def user1 = UserMotherObject.aUser("aragorn@middleearth.com")
        userRepository.save(user1)

        when:
        def result = mvc.perform(get("/users/basic?email=" + user1.getEmail()))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(user1.getFirstName())
    }
}
