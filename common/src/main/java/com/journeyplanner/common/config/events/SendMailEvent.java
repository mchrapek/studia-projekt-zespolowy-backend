package com.journeyplanner.common.config.events;

import lombok.*;

import java.util.Map;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMailEvent {

    String to;

    String templateName;

    Map<String, String> params;
}
