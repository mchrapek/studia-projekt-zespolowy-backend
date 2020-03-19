package com.journeyplanner.catalogue.domain.journey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class JourneyRepositoryInMemory implements JourneyRepository {

    private final ConcurrentHashMap<String, Journey> db = new ConcurrentHashMap<>();

    @Override
    public Page<Journey> findAllByStartAfter(Instant now, Pageable pageable) {
        return null;
    }

    @Override
    public Journey save(Journey journey) {
        return null;
    }

    @Override
    public Optional<Journey> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }
}
