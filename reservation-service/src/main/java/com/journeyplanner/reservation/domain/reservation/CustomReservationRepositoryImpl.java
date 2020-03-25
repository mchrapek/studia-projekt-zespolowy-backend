package com.journeyplanner.reservation.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@AllArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateReservationStatusTo(final String id, final ReservationStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);

        mongoTemplate.updateFirst(query, update, Reservation.class);
    }
}
