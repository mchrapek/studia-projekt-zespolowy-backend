package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.TransferType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TransferScheduler {

    private final TransferRepository transferRepository;
    private final AccountFacade accountFacade;

    @Scheduled(cron = "${transfer.cron}")
    public void fetch() {
        transferRepository.findPendingAndModifyStatus()
                .ifPresent(this::makeTransfer);
    }

    public void makeTransfer(Transfer transfer) {
        if (transfer.getType() == TransferType.LOAD) {
            accountFacade.loadTransfer(transfer);
        } else if (transfer.getType() == TransferType.RETURN) {
            accountFacade.returnTransfer(transfer);
        }
        transferRepository.findAndModifyStatus(transfer.getId(), TransferStatus.DONE);
    }
}
