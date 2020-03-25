package com.journeyplanner.user.domain.password;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import com.journeyplanner.user.infrastructure.output.queue.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.UUID;

import static java.text.MessageFormat.format;

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
        log.info(format("New reset token generated : {0} : for email {1}", resetToken.getToken(), email));

        mailSender.publish(SendMailEvent.builder()
                .id(UUID.randomUUID().toString())
                .to(email)
                .templateName(Template.RESET_PASSWORD.getPath())
                .params(new HashMap<String, String>() {{
                    put("firstName", firstName);
                    put("token", resetToken.getToken());
                }})
                .build());
        log.info(format("Mail sent with token : {0} : to user : {1}", resetToken.getToken(), email));
    }

    public void validateToken(final String token, final String email) {
        ResetToken resetToken = resetTokenRepository
                .deprecateToken(token)
                .orElseThrow(() -> new ResourceNotFound(format("Token with this id {0} doesn't exists", token)));

        if (!resetToken.getEmail().equals(email)) {
            throw new ResourceNotFound(format("Token with this id {0} doesn't exists for this email {1}", token, email));
        }

        log.info(format("Token : {0} : is valid for email : {1}", token, email));
    }

    public String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }
}
