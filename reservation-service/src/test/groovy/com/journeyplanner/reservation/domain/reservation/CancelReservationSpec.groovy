package com.journeyplanner.reservation.domain.reservation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CancelReservationSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ReservationRepository repository

    def setup() {
        repository.deleteAll();
    }

    def "should cancel reservation"() {
        given:
        def email = "james@bond.com"
        def reservation = ReservationMotherObject.aReservation(email)
        repository.save(reservation)

        when:
        mvc.perform(delete("/reservations/" + reservation.getId())
                .header("x-username", email))
                .andExpect(status().isNoContent())
                .andReturn()

        then:
        repository.getReservationByEmail(email).get(0).id == reservation.getId()
        repository.getReservationByEmail(email).get(0).status == ReservationStatus.CANCEL
    }

    def "should fail when cancel reservation 14 days before"() {
        given:
        def email = "james@bond.com"
        def reservation = ReservationMotherObject.aReservation(email, Instant.now().plus(2, ChronoUnit.DAYS))
        repository.save(reservation)

        when:
        mvc.perform(delete("/reservations/" + reservation.getId())
                .header("x-username", email))
                .andExpect(status().is4xxClientError())
                .andReturn()

        then:
        repository.getReservationByEmail(email).get(0).id == reservation.getId()
        repository.getReservationByEmail(email).get(0).status == ReservationStatus.ACTIVE
    }
}
