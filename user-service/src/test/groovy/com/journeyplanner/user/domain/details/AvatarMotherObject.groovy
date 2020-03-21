package com.journeyplanner.user.domain.details

import com.journeyplanner.user.domain.avatar.Avatar
import org.bson.BsonBinarySubType
import org.bson.types.Binary

class AvatarMotherObject {

    static aAvatar(String mail, String content = "AVATAR") {
        Avatar.builder()
                .id(UUID.randomUUID().toString())
                .mail(mail)
                .image(new Binary(BsonBinarySubType.BINARY, content.getBytes()))
                .build()
    }
}
