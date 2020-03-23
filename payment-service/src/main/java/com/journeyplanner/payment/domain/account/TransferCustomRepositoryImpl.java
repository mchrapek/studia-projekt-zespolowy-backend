package com.journeyplanner.payment.domain.account;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

@AllArgsConstructor
public class TransferCustomRepositoryImpl implements TransferCustomRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Transfer> findPendingAndModifyStatus() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(TransferStatus.PENDING));
        query.limit(1);
        query.with(Sort.by(Sort.Direction.ASC, "eventTime"));

        Update update = new Update();
        update.set("status", TransferStatus.PROCESSING);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, Transfer.class));
    }

    @Override
    public Optional<Transfer> findAndModifyStatus(String id, TransferStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, Transfer.class));
    }
}
