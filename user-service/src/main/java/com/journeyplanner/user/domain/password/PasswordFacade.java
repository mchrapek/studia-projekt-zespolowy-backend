package com.journeyplanner.user.domain.password;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import com.journeyplanner.user.infrastructure.output.queue.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@Slf4j
@AllArgsConstructor
public class PasswordFacade {

    private final ResetTokenRepository resetTokenRepository;
    private final ResetTokenCreator resetTokenCreator;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public void generateAndSendResetPasswordLinkWithToken(final String email, final String firstName) {
        ResetToken resetToken = resetTokenCreator.from(email);
        resetTokenRepository.save(resetToken);

        mailSender.publish(SendMailEvent.builder()
                .to(email)
                .templateName(Template.RESET_PASSWORD.getPath())
                .params(new HashMap<String, String>() {{
                    put("firstName", firstName);
                    put("link", resetToken.getToken());
                }})
                .build());
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
