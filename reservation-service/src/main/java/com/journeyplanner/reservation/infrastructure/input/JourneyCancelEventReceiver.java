package com.journeyplanner.reservation.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journeyplanner.common.config.events.CancelJourneyEvent;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor
public class JourneyCancelEventReceiver {

    private final ReservationFacade reservationFacade;

    @RabbitListener(queues = "${queue.reservation.name}")
    public void publish(String event) {
        try {
            CancelJourneyEvent cancelJourneyEvent = new ObjectMapper().readValue(event, CancelJourneyEvent.class);
            log.info(format("Event received : {0}", cancelJourneyEvent.toString()));
            reservationFacade.createNewCancelEvent(cancelJourneyEvent);
        } catch (Exception e) {
            log.error(format("Malformed event received : {0}", event));
        }
    }
}
