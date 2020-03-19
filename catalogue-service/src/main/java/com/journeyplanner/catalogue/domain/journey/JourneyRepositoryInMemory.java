package com.journeyplanner.catalogue.domain.journey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.ConcurrentHashMap;

class JourneyRepositoryInMemory implements JourneyRepository {

    private final ConcurrentHashMap<String, Journey> db = new ConcurrentHashMap<>();

    @Override
    public Page<Journey> findAll(Pageable pageable) {
        return null;
    }
}
