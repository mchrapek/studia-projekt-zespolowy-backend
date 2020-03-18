package com.journeyplanner.user.domain.password;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

@AllArgsConstructor
public class ResetTokenCustomRepositoryImpl implements ResetTokenCustomRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public Optional<ResetToken> deprecateToken(final String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));

        Update update = new Update();
        update.set("active", Boolean.FALSE);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, ResetToken.class));
    }
}
