package com.journeyplanner.catalogue.domain.journey;

interface CustomJourneyRepository {

    void updateJourneyStatus(String id, JourneyStatus status);
}
