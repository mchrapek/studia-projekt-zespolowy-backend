package com.journeyplanner.user.domain.resettoken;

import java.util.concurrent.ConcurrentHashMap;

public class ResetTokenRepositoryInMemory implements ResetTokenRepository {

    private ConcurrentHashMap<String, ResetToken> db = new ConcurrentHashMap<>();

    @Override
    public ResetToken save(ResetToken resetToken) {
        return db.put(resetToken.getToken(), resetToken);
    }
}
