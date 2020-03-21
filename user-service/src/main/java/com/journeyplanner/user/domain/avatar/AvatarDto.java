package com.journeyplanner.user.domain.avatar;

import lombok.Builder;
import lombok.Value;
import org.bson.types.Binary;

@Value
@Builder
public class AvatarDto {

    String id;

    String mail;

    Binary image;

    static AvatarDto from(Avatar avatar) {
        return AvatarDto.builder()
                .id(avatar.getId())
                .mail(avatar.getMail())
                .image(avatar.getImage())
                .build();
    }
}
