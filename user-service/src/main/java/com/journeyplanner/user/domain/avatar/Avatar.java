package com.journeyplanner.user.domain.avatar;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(value = "avatar")
class Avatar {

    @Id
    @NonNull String id;

    @Indexed(unique = true)
    @NonNull String email;

    @NonNull Binary image;
}
