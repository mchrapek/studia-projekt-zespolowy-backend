package com.journeyplanner.catalogue.domain.photo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(collection = "photos")
class Photo {

    @Id
    @NonNull String id;

    @NonNull String journeyId;

    @NonNull Binary image;
}
