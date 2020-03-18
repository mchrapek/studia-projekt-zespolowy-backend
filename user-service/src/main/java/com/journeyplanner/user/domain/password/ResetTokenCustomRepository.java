package com.journeyplanner.user.domain.password;

import java.util.Optional;

interface ResetTokenCustomRepository {

    Optional<ResetToken> deprecateToken(String token);
}
