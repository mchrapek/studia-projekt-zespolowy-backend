package com.journeyplanner.user.domain.password;

import org.springframework.data.repository.Repository;

import java.util.List;

interface ResetTokenRepository extends Repository<ResetToken, String>, ResetTokenCustomRepository {

    List<ResetToken> findByEmail(String email);

    ResetToken save(ResetToken resetToken);

    void deleteAll();
}
