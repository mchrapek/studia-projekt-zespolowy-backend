package com.journeyplanner.catalogue.domain.photo;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public class PhotoCreator {

    Optional<Photo> from(String journeyId, MultipartFile file) {
        try {
            return Optional.ofNullable(Photo.builder()
                    .id(UUID.randomUUID().toString())
                    .journeyId(journeyId)
                    .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
