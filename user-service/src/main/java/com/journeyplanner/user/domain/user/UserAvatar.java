package com.journeyplanner.user.domain.user;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(value = "userAvatar")
class UserAvatar {

    @Id
    @NonNull String id;

    @Indexed(unique = true)
    @NonNull String email;

    @NonNull Binary image;
}
