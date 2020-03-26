package com.journeyplanner.reservation.infrastructure.input;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor
public class ReservationCreateEventReceiver {

    private final ReservationFacade reservationFacade;

    @RabbitListener(queues = "${queue.reservation.name}")
    public void publish(String event) {
        try {
            CreateReservationEvent reservationEvent = new ObjectMapper().findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).readValue(event, CreateReservationEvent.class);
            log.info(format("Event received : {0}", reservationEvent.toString()));
            reservationFacade.createNew(reservationEvent);
        } catch (Exception e) {
            log.error(format("Malformed event received : {0}", event));
        }
    }
}
