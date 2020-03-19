package com.journeyplanner.catalogue.domain.journey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.time.Instant;

interface JourneyRepository extends Repository<Journey, String> {

    Page<Journey> findAllByStartAfter(Instant now, Pageable pageable);
}
