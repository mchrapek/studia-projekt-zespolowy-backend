package com.journeyplanner.catalogue.domain.journey;

import com.journeyplanner.catalogue.exceptions.ResourcesNotFound;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateReservationRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.output.queue.ReservationCreator;
import com.journeyplanner.common.config.events.CreateReservationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class JourneyFacade {

    private final JourneyRepository repository;
    private final JourneyCreator journeyCreator;
    private final JourneyUpdater journeyUpdater;
    private final ReservationCreator reservationCreator;

    public Page<JourneyDto> getAll(Pageable pageable) {
        return repository.findAllByStartAfter(Instant.now(), pageable).map(JourneyDto::from);
    }

    public JourneyDto create(CreateJourneyRequest request) {
        Journey savedJourney = repository.save(journeyCreator.from(request));
        return JourneyDto.from(savedJourney);
    }

    public JourneyDto update(UpdateJourneyRequest request) {
        if (!repository.existsById(request.getId())) {
            throw new ResourcesNotFound(format("Cannot found journey with id : {0}", request.getId()));
        }

        Journey updatedJourney = repository.save(journeyUpdater.from(request));
        return JourneyDto.from(updatedJourney);
    }

    public void createReservation(CreateReservationRequest request, String username) {
        Journey journey = repository.findById(request.getId())
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found journey with id : {0}", request.getId())));

        reservationCreator.publish(CreateReservationEvent.builder()
                .id(journey.getId())
                .email(username)
                .journeyId(journey.getId())
                .price(journey.getPrice())
                .eventTimeStamp(Instant.now())
                .build());
    }
}
