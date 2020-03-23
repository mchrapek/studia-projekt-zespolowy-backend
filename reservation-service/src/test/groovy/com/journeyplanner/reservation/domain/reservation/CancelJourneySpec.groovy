package com.journeyplanner.reservation.domain.reservation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CancelJourneySpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ReservationRepository reservationRepository

    @Autowired
    private CancelJourneyRuleRepository cancelJourneyRuleRepository

    @Autowired
    private CancelJourneyScheduler scheduler

    def setup() {
        reservationRepository.deleteAll()
        cancelJourneyRuleRepository.deleteAll()
    }

    def "should cancel all reservations"() {
        given:
        def journeyId1 = UUID.randomUUID().toString()
        def journeyId2 = UUID.randomUUID().toString()
        def userEmail1 = "james@bond.com"
        def userEmail2 = "frodo@baggins.com"
        def userEmail3 = "sam@shire.com"
        def reservation1 = ReservationMotherObject.aReservation(userEmail1, Instant.now().plus(10, ChronoUnit.DAYS), journeyId1)
        def reservation2 = ReservationMotherObject.aReservation(userEmail2, Instant.now().plus(10, ChronoUnit.DAYS), journeyId1)
        def reservation3 = ReservationMotherObject.aReservation(userEmail3, Instant.now().plus(10, ChronoUnit.DAYS), journeyId2)
        reservationRepository.save(reservation1)
        reservationRepository.save(reservation2)
        reservationRepository.save(reservation3)

        and:
        def rule = CancelJourneyRuleMotherObject.aRule(journeyId1)
        cancelJourneyRuleRepository.save(rule)

        when:
        scheduler.cancel()

        then:
        reservationRepository.findById(reservation1.getId()).get().status == ReservationStatus.CANCEL
        reservationRepository.findById(reservation2.getId()).get().status == ReservationStatus.CANCEL
        reservationRepository.findById(reservation3.getId()).get().status == ReservationStatus.ACTIVE
        cancelJourneyRuleRepository.findAllByStatus(CancelJourneyRuleStatus.ACTIVE).size() == 1
        cancelJourneyRuleRepository.findAllByStatus(CancelJourneyRuleStatus.INACTIVE).size() == 0
    }

    def "should deactivate rule after expiration time"() {
        given:
        def journeyId = UUID.randomUUID().toString()
        def userEmail = "james@bond.com"
        def reservation = ReservationMotherObject.aReservation(userEmail, Instant.now().plus(10, ChronoUnit.DAYS), journeyId)
        reservationRepository.save(reservation)

        and:
        def rule = CancelJourneyRuleMotherObject.aRule(journeyId, Instant.now().plus(60, ChronoUnit.MINUTES))
        cancelJourneyRuleRepository.save(rule)

        when:
        scheduler.cancel()

        then:
        reservationRepository.findById(reservation.getId()).get().status == ReservationStatus.CANCEL
        cancelJourneyRuleRepository.findAllByStatus(CancelJourneyRuleStatus.ACTIVE).size() == 0
        cancelJourneyRuleRepository.findAllByStatus(CancelJourneyRuleStatus.INACTIVE).size() == 1
    }
}
