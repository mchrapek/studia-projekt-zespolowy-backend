package com.journeyplanner.catalogue.domain.journey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;

interface JourneyRepository extends Repository<Journey, String> {

    Page<Journey> findAllByStartAfter(Instant now, Pageable pageable);

    Journey save(Journey journey);

    Optional<Journey> findById(String id);

    boolean existsById(String id);

    void deleteAll();
}
