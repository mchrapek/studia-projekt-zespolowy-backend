package com.journeyplanner.user.domain.password;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document(collection = "resetToken")
class ResetToken {

    @Id
    @NonNull String token;

    @NonNull String email;

    @NonNull boolean active;
}
