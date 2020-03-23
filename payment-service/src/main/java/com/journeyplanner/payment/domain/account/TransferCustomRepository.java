package com.journeyplanner.payment.domain.account;

import java.util.Optional;

interface CustomTransferRepository {

    Optional<Transfer> findPendingAndModifyStatus();

    Optional<Transfer> findAndModifyStatus(String id, TransferStatus status);
}
