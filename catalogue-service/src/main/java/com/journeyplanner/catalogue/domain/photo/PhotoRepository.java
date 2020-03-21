package com.journeyplanner.catalogue.domain.photo;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface PhotoRepository extends Repository<Photo, String> {

    void save(Photo photo);

    Optional<Photo> findById(String id);

    List<Photo> findAllByJourneyId(String journeyId);

    void deleteAll();
}
