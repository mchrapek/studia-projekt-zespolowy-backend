package com.journeyplanner.catalogue.domain.journey;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@AllArgsConstructor
class CustomJourneyRepositoryImpl implements CustomJourneyRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateJourneyStatus(String id, JourneyStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);

        mongoTemplate.updateFirst(query, update, Journey.class);
    }
}
