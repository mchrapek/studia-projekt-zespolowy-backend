package com.journeyplanner.payment.domain.account;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.payment.exceptions.IllegalOperation;
import com.journeyplanner.payment.infrastructure.output.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
class TransferScheduler {

    private final TransferRepository transferRepository;
    private final AccountFacade accountFacade;
    private final MailSender mailSender;

    @Scheduled(cron = "${transfer.cron}")
    public void fetch() {
        Optional<Transfer> transfer = transferRepository.findPendingAndModifyStatus();
        try {
            transfer.ifPresent(this::makeTransfer);
        } catch (Exception e) {
            transfer.ifPresent(this::sendMailWithError);
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

    public void sendMailWithError(Transfer transfer) {
        mailSender.publish(SendMailEvent.builder().to(transfer.getEmail()).templateName(Template.PAYMENT_ERROR.getPath())
                .params(new HashMap<String, String>() {{
                    put("value", transfer.getValue().toPlainString());
                }}).build());
    }
}
