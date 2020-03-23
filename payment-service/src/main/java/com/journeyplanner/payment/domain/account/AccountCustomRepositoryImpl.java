package com.journeyplanner.payment.domain.account;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void modifyAccountBalance(String id, BigDecimal balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("balance", balance);

        mongoTemplate.updateFirst(query, update, Account.class);
    }

    @Override
    public void modifyAccountBalanceAndEventTime(String id, Instant eventTime, BigDecimal balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("balance", balance);
        update.set("lastEventTime", eventTime);

        mongoTemplate.updateFirst(query, update, Account.class);
    }
}
