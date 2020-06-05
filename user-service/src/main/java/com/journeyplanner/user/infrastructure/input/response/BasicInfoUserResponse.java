package com.journeyplanner.user.infrastructure.input.response;

import com.journeyplanner.user.domain.user.UserDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasicInfoUserResponse {

    String id;
    String email;
    String firstName;
    String secondName;

    public static BasicInfoUserResponse from(UserDto userDto) {
        return BasicInfoUserResponse.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .build();
    }
}
