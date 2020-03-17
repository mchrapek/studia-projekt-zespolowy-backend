package com.journeyplanner.mail.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class MailService {

    private MailRepository repository;

    void createPendingMail(Mail mail) {
        mail.setPendingStatus();
        repository.save(mail);
    }

    Optional<Mail> getPendingMessageAndUpdateToProcessingStatus() {
        return repository.findPendingMessageAndUpdateStatusTo(MailStatus.PROCESSING);
    }

    void updateStatus(String id, MailStatus status) {
        repository.updateMailStatusTo(id, status);
    }
}
