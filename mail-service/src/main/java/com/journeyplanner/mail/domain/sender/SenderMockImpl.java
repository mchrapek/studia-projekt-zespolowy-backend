package com.journeyplanner.mail.domain.sender;

import com.journeyplanner.mail.domain.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@Profile("!email")
class SenderMockImpl implements Sender {

    @Override
    public void send(Mail mail, String body) {
        log.info("Use MOCK sender implementation");

        log.info(format("Start sending mail with id : {0}", mail.getId()));
        log.info(format("Mail : {0}", body));
    }
}
