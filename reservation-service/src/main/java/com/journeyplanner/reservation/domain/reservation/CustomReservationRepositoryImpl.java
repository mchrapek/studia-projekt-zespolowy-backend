package com.journeyplanner.reservation.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@AllArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateReservationStatusTo(String id, ReservationStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);

        mongoTemplate.updateFirst(query, update, Reservation.class);
    }
}
