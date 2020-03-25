package com.journeyplanner.user.domain.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GuideDto {

    String email;
    String firstName;
    String secondName;

    static GuideDto from(User user) {
        return GuideDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .build();
    }
}
