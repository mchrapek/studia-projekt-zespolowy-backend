package com.journeyplanner.payment.domain.account;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface TransferRepository extends Repository<Transfer, String>, TransferCustomRepository {

    Transfer save(Transfer transfer);

    Optional<Transfer> findById(String id);

    void deleteAll();
}
