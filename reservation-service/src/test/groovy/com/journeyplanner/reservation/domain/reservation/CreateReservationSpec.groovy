package com.journeyplanner.reservation.domain.reservation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CreateReservationSpec extends Specification {

    @Autowired
    private ReservationFacade facade

    @Autowired
    private ReservationRepository repository

    def setup() {
        repository.deleteAll();
    }

    def "should create new reservation"() {
        given:
        def email = "james@bond.com"
        def createReservationEvent = CreateReservationMotherObject.aCreateReservation(email)

        when:
        facade.createNew(createReservationEvent)

        then:
        repository.findAll().size() == 1
    }
}
