package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.exceptions.ResourcesNotFound;
import com.journeyplanner.catalogue.infrastructure.input.request.AddGuideToJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.output.queue.CancelJourney;
import com.journeyplanner.catalogue.infrastructure.output.queue.ReservationCreator;
import com.journeyplanner.common.config.events.CancelJourneyEvent;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class JourneyFacade {

    private final JourneyRepository repository;
    private final JourneyCreator journeyCreator;
    private final JourneyUpdater journeyUpdater;
    private final ReservationCreator reservationCreator;
    private final CancelJourney cancelJourney;

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

    public JourneyDto update(final String journeyId, final UpdateJourneyRequest request) {
        Journey journey = repository.findById(journeyId)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", journeyId)));

        Journey updatedJourney = repository.save(journeyUpdater.from(journey, request));
        log.info(format("Journey updated : {0}", updatedJourney.getId()));

        return JourneyDto.from(updatedJourney);
    }

    public void cancel(final String id) {
        Journey journey = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", id)));

        if (journey.getStatus() == JourneyStatus.ACTIVE) {
            repository.updateJourneyStatus(journey.getId(), JourneyStatus.INACTIVE);
        }

        cancelJourney.publish(CancelJourneyEvent.builder()
                .id(UUID.randomUUID().toString())
                .journeyId(journey.getId())
                .eventTimeStamp(Instant.now())
                .build());
    }

    public void createReservation(final String journeyId, final String username) {
        Journey journey = repository.findById(journeyId)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", journeyId)));

        if (journey.getStatus() == JourneyStatus.INACTIVE) {
            throw new ResourcesNotFound(format("Journey : {0} : is inactive", journeyId));
        }

        log.info(format("Request for reservation for journey : {0}", journeyId));
        reservationCreator.publish(CreateReservationEvent.builder()
                .id(UUID.randomUUID().toString())
                .start(journey.getStart())
                .end(journey.getEnd())
                .email(username)
                .journeyId(journey.getId())
                .journeyName(journey.getName())
                .price(journey.getPrice())
                .eventTimeStamp(Instant.now())
                .build());
    }

    public JourneyDto addGuide(final String journeyId, final AddGuideToJourneyRequest request) {
        Journey journey = repository.findById(journeyId)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", journeyId)));

        Journey updatedJourney;
        if (request.getEmail().isEmpty()) {
            updatedJourney = repository.save(journeyUpdater.fromWithoutGuide(journey));
        } else {
            updatedJourney = repository.save(journeyUpdater.from(journey, request));
        }
        log.info(format("Journey Guide updated : {0}", updatedJourney.getId()));

        return JourneyDto.from(updatedJourney);
    }

    public List<JourneyDto> getGuideJourneys(final String email) {
        return repository.findByGuideEmail(email)
                .stream()
                .map(JourneyDto::from)
                .collect(Collectors.toList());
    }
}
