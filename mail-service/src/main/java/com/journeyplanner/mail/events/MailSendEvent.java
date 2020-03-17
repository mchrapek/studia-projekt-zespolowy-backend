package com.journeyplanner.mail.events;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@ToString
public class MailSendEvent {

    String to;

    String templateName;

    Map<String, String> params;
}
