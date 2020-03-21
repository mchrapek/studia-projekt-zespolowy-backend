package com.journeyplanner.catalogue.domain.photo

import org.bson.BsonBinarySubType
import org.bson.types.Binary

class PhotoMotherObject {

    static aPhoto(String journeyId, String content = "PHOTO") {
        Photo.builder()
                .id(UUID.randomUUID().toString())
                .journeyId(journeyId)
                .image(new Binary(BsonBinarySubType.BINARY, content.getBytes()))
                .build()
    }
}
