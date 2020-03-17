package com.journeyplanner.mail.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Builder
@Document(collection = "mail")
public class Mail {

    @Id
    private String id;

    private String to;

    private MailStatus status;

    private String templateName;

    private Map<String, String> params;

    void setPendingStatus() {
        this.status = MailStatus.PENDING;
    }

    void setErrorStatus() {
        this.status = MailStatus.ERROR;
    }

    void setSentStatus() {
        this.status = MailStatus.SENT;
    }
}
