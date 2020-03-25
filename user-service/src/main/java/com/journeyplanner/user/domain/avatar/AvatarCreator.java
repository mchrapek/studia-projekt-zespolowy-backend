package com.journeyplanner.user.domain.avatar;

import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Slf4j
class AvatarCreator {

    Optional<Avatar> from(final String email, final MultipartFile file) {
        try {
            return Optional.ofNullable(Avatar.builder()
                    .id(UUID.randomUUID().toString())
                    .email(email)
                    .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build());
        } catch (Exception e) {
            log.warn(format("Cannot parse avatar for user {0} : {1}", email, e.getMessage()));
            return Optional.empty();
        }
    }

    Optional<Avatar> from(final String id, String email, final MultipartFile file) {
        try {
            return Optional.ofNullable(Avatar.builder()
                    .id(id)
                    .email(email)
                    .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build());
        } catch (Exception e) {
            log.warn(format("Cannot parse avatar for user {0} : {1}", email, e.getMessage()));
            return Optional.empty();
        }
    }
}
