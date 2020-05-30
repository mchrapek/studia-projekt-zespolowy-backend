package com.journeyplanner.user.domain.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    String id;
    String email;
    String firstName;
    String secondName;
    String role;
    boolean isBlocked;
    boolean newPasswordRequired;

    static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .role(user.getRole())
                .isBlocked(user.isBlocked())
                .newPasswordRequired(user.isNewPasswordRequired())
                .build();
    }
}
