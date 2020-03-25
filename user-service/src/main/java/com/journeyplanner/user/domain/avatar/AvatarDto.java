package com.journeyplanner.user.domain.avatar;

import lombok.Builder;
import lombok.Value;
import org.bson.types.Binary;

@Value
@Builder
public class AvatarDto {

    String id;

    String email;

    Binary image;

    static AvatarDto from(Avatar avatar) {
        return AvatarDto.builder()
                .id(avatar.getId())
                .email(avatar.getEmail())
                .image(avatar.getImage())
                .build();
    }
}
