package com.journeyplanner.catalogue.domain.journey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@Slf4j
@AllArgsConstructor
public class JourneyFacade {

    private final JourneyRepository repository;

    public Page<JourneyDto> getAll(Pageable pageable) {
        return repository.findAllByStartAfter(Instant.now(), pageable).map(JourneyDto::from);
    }
}
