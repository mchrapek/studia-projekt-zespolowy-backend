package com.journeyplanner.mail.domain;

import java.util.Optional;

interface CustomMailRepository {

    Optional<Mail> findPendingMessageAndUpdateStatusTo(MailStatus mailStatus);

    void updateMailStatusTo(String id, MailStatus status);
}