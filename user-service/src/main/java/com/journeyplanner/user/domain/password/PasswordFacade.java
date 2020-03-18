package com.journeyplanner.user.domain.password;

import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@AllArgsConstructor
public class PasswordFacade {

    private ResetTokenRepository resetTokenRepository;
    private ResetTokenCreator resetTokenCreator;
    private PasswordEncoder passwordEncoder;

    public void generateAndSendResetPasswordLinkWithToken(final String email) {
        // TODO deprecate old tokens
        ResetToken resetToken = resetTokenCreator.from(email);
        resetTokenRepository.save(resetToken);
        // TODO send email
    }

    public void validateToken(final String token, final String email) {
        ResetToken resetToken = resetTokenRepository
                .deprecateToken(token)
                .orElseThrow(() -> new ResourceNotFound("Token with this id doesn't exists"));

        if (!resetToken.getEmail().equals(email)) {
            throw new ResourceNotFound("Token with this id doesn't exists for this email");
        }
    }

    public String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }
}
