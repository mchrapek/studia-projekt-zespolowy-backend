package com.journeyplanner.user.domain.avatar;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

class AvatarCreator {

    Optional<Avatar> from(String mail, MultipartFile file) {
        try {
            return Optional.ofNullable(Avatar.builder()
                    .id(UUID.randomUUID().toString())
                    .mail(mail)
                    .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    Optional<Avatar> from(String id, String mail, MultipartFile file) {
        try {
            return Optional.ofNullable(Avatar.builder()
                    .id(id)
                    .mail(mail)
                    .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
