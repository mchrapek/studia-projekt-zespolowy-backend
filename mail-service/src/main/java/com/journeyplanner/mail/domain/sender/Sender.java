package com.journeyplanner.mail.domain.sender;

import com.journeyplanner.mail.domain.Mail;

public interface Sender {

    void send(Mail mail, String body);
}
