package com.journeyplanner.user.domain.user;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.user.domain.exceptions.UserWithEmailAlreadyExists;
import com.journeyplanner.user.domain.password.PasswordFacade;
import com.journeyplanner.user.infrastructure.input.request.*;
import com.journeyplanner.user.infrastructure.output.queue.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class UserFacade {

    private final UserRepository repository;
    private final UserCreator userCreator;
    private final MailSender mailSender;
    private final PasswordFacade passwordFacade;

    public void createUser(final CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExists(format("User with email {0} already exists", request.getEmail()));
        }

        User userToSave = userCreator.from(request, passwordFacade.encodePassword(request.getPassword()));
        repository.save(userToSave);

        mailSender.publish(SendMailEvent.builder()
                .to(userToSave.getEmail())
                .templateName(Template.NEW_USER.getPath())
                .params(new HashMap<String, String>() {{
                    put("firstName", userToSave.getFirstName());
                    put("secondName", userToSave.getSecondName());
                }})
                .build());
    }

    public void sendResetPasswordToken(final GenerateResetPasswordLinkRequest request) {
        passwordFacade.generateAndSendResetPasswordLinkWithToken(request.getEmail());
    }

    public void resetPassword(final ResetPasswordRequest request) {
        passwordFacade.validateToken(request.getToken(), request.getEmail());
        repository.updatePassword(request.getEmail(), passwordFacade.encodePassword(request.getNewPassword()));
    }

    public void block(final AddUserToBlacklistRequest request) {
        repository.changeIsBlacklisted(request.getEmail(), Boolean.TRUE);
    }

    public void unblock(final RemoveUserFromBlacklistRequest request) {
        repository.changeIsBlacklisted(request.getEmail(), Boolean.FALSE);
    }

    public Page<UserDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserDto::from);
    }
}
