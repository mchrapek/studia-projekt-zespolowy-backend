package com.journeyplanner.mail.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

@AllArgsConstructor
class CustomMailRepositoryImpl implements CustomMailRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Mail> findPendingMessageAndUpdateStatusTo(MailStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(MailStatus.PENDING));

        Update update = new Update();
        update.set("status", status);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, Mail.class));
    }

    @Override
    public void updateMailStatusTo(String id, MailStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);

        mongoTemplate.updateFirst(query, update, Mail.class);
    }
}
