package com.journeyplanner.reservation.domain.reservation

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
class GetReservationSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ReservationRepository repository

    def setup() {
        repository.deleteAll();
    }

    def "should get user reservation"() {
        given:
        def email = "james@bond.com"
        def reservation = ReservationMotherObject.aReservation(email)
        repository.save(reservation)

        when:
        def result = mvc.perform(get("/")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(reservation.getId())
    }
}



