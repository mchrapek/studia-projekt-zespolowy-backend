package com.journeyplanner.reservation.domain.reservation;

import com.journeyplanner.reservation.infrastructure.output.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationConfiguration {

    @Bean
    ReservationFacade reservationFacade(ReservationRepository reservationRepository, CancelJourneyRuleRepository cancelJourneyRuleRepository, MailSender mailSender) {
        return new ReservationFacade(reservationRepository, new ReservationCreator(), new CancelJourneyRuleCreator(), cancelJourneyRuleRepository, mailSender);
    }
}
