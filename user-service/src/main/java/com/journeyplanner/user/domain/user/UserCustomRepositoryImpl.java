package com.journeyplanner.user.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@AllArgsConstructor
class UserCustomRepositoryImpl implements UserCustomRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updatePassword(final String email, final String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        Update update = new Update();
        update.set("password", Boolean.FALSE);

        mongoTemplate.updateFirst(query, update, User.class);
    }
}
