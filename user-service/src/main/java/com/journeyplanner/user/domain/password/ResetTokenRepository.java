package com.journeyplanner.user.domain.password;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ResetTokenRepository extends Repository<ResetToken, String>, ResetTokenCustomRepository {

    Optional<ResetToken> findByToken(String token);

    List<ResetToken> findByEmail(String email);

    ResetToken save(ResetToken resetToken);
}
