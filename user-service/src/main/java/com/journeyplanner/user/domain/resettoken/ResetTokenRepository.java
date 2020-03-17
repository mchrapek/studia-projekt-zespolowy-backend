package com.journeyplanner.user.domain.resettoken;

import org.springframework.data.repository.Repository;

interface ResetTokenRepository extends Repository<ResetToken, String> {

    ResetToken save(ResetToken resetToken);
}
