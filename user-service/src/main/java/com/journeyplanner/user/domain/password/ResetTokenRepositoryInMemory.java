package com.journeyplanner.user.domain.password;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ResetTokenRepositoryInMemory implements ResetTokenRepository {

    private ConcurrentHashMap<String, ResetToken> db = new ConcurrentHashMap<>();

    @Override
    public Optional<ResetToken> findByToken(String token) {
        return Optional.ofNullable(db.get(token));
    }

    @Override
    public List<ResetToken> findByEmail(String email) {
        return db.values()
                .stream()
                .filter(r -> r.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    @Override
    public ResetToken save(ResetToken resetToken) {
        return db.put(resetToken.getToken(), resetToken);
    }

    @Override
    public Optional<ResetToken> deprecateToken(String token) {
        if (!db.keySet().contains(token)) {
            return Optional.empty();
        }

        ResetToken resetToken = db.get(token);
        ResetToken updatedResetToken = ResetToken.builder()
                .token(resetToken.getToken())
                .email(resetToken.getEmail())
                .active(Boolean.FALSE)
                .build();
        db.put(resetToken.getToken(), updatedResetToken);

        return Optional.of(resetToken);
    }
}
