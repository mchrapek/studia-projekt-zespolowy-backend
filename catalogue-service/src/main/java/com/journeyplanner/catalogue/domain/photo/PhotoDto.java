package com.journeyplanner.catalogue.domain.photo;

import lombok.Builder;
import lombok.Value;
import org.bson.types.Binary;

@Value
@Builder
public class PhotoDto {

    String id;

    String journeyId;

    Binary image;

    static PhotoDto from(final Photo photo) {
        return PhotoDto.builder()
                .id(photo.getId())
                .journeyId(photo.getJourneyId())
                .image(photo.getImage())
                .build();
    }
}
