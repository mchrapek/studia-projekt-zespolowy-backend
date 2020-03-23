package com.journeyplanner.payment.domain.account;

import com.journeyplanner.payment.exceptions.IllegalOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
class TransferScheduler {

    private final TransferRepository transferRepository;
    private final AccountFacade accountFacade;

    @Scheduled(cron = "${transfer.cron}")
    public void fetch() {
        try {
            transferRepository.findPendingAndModifyStatus()
                    .ifPresent(this::makeTransfer);
        } catch (Exception e) {
            log.error("ERROR : " + e.getMessage());
        }
    }

    public void makeTransfer(Transfer transfer) {
        switch (transfer.getType()) {
            case LOAD:
                accountFacade.loadTransfer(transfer);
                break;
            case RETURN:
                accountFacade.returnTransfer(transfer);
                break;
            default:
                throw new IllegalOperation("Not supported operation type");
        }
        transferRepository.findAndModifyStatus(transfer.getId(), TransferStatus.DONE);
    }
}
