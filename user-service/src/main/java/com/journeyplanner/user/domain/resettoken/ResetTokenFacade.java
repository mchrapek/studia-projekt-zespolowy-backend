package com.journeyplanner.user.domain.resettoken;

import com.journeyplanner.user.infrastructure.request.ResetPasswordLinkRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ResetTokenFacade {

    private ResetTokenRepository resetTokenRepository;
    private ResetTokenCreator resetTokenCreator;

    public void generateAndSendResetPasswordLinkWithToken(ResetPasswordLinkRequest request) {
        // TODO check user
        ResetToken resetToken = resetTokenCreator.from(request);
        resetTokenRepository.save(resetToken);
        // TODO send email
    }
}
