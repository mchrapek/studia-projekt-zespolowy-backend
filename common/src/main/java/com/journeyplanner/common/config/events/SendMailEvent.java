package com.journeyplanner.common.config.events;

import lombok.*;

import java.util.Map;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMailEvent implements Event {

    @NonNull String to;

    @NonNull String templateName;

    @NonNull Map<String, String> params;
}
