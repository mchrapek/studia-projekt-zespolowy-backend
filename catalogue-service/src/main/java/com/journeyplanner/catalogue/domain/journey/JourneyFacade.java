package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.exceptions.ResourcesNotFound;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.output.queue.ReservationCreator;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class JourneyFacade {

    private final JourneyRepository repository;
    private final JourneyCreator journeyCreator;
    private final JourneyUpdater journeyUpdater;
    private final ReservationCreator reservationCreator;

    public Page<JourneyDto> getAll(final Predicate predicate, final Pageable pageable) {
        return repository
                .findAll(predicate, pageable)
                .map(JourneyDto::from);
    }

    public JourneyDto create(final CreateJourneyRequest request) {
        Journey savedJourney = repository.save(journeyCreator.from(request));

        log.info(format("Journey created : {0}", savedJourney.getId()));
        return JourneyDto.from(savedJourney);
    }

    public JourneyDto update(String journeyId, UpdateJourneyRequest request) {
        Journey journey = repository.findById(journeyId)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", journeyId)));

        Journey updatedJourney = repository.save(journeyUpdater.from(journey, request));
        log.info(format("Journey updated : {0}", updatedJourney.getId()));

        return JourneyDto.from(updatedJourney);
    }

    public void cancel(String id) {
        Journey journey = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", id)));

        if (journey.getStatus() == JourneyStatus.ACTIVE) {
            repository.updateJourneyStatus(journey.getId(), JourneyStatus.INACTIVE);
        }
    }

    public void createReservation(String journeyId, String username) {
        Journey journey = repository.findById(journeyId)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", journeyId)));

        log.info(format("Request for reservation for journey : {0}", journeyId));
        reservationCreator.publish(CreateReservationEvent.builder()
                .id(UUID.randomUUID().toString())
                .start(journey.getStart())
                .end(journey.getEnd())
                .email(username)
                .journeyId(journey.getId())
                .price(journey.getPrice())
                .eventTimeStamp(Instant.now())
                .build());
    }
}
