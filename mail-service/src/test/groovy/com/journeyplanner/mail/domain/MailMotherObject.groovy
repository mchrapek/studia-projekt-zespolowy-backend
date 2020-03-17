package com.journeyplanner.mail.domain

import com.journeyplanner.mail.events.MailSendEvent

class MailMotherObject {

    static Mail aMail(String id, MailStatus status) {
        Mail.builder()
                .id(id)
                .to("test@testowy.pl")
                .templateName("test.vm")
                .status(status)
                .build()
    }

    static MailSendEvent aMailSendEvent() {
        def params = new HashMap()
        params.put("param", "value")

        MailSendEvent.builder()
                .to("test@test.pl")
                .templateName("test.vm")
                .params(params)
                .build()
    }
}
