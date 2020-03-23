package com.journeyplanner.payment.domain.account;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TransferRepository extends Repository<Transfer, String>, TransferCustomRepository {

    List<Transfer> findAll();

    Transfer save(Transfer transfer);

    Optional<Transfer> findFirstByPaymentIdOrderByEventTimeDesc(String paymentId);

    Optional<Transfer> findById(String id);

    void deleteAll();
}
