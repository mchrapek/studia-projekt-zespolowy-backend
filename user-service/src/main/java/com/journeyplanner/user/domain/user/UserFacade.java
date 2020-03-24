package com.journeyplanner.user.domain.user;

import com.journeyplanner.common.config.events.SendMailEvent;
import com.journeyplanner.common.config.mail.Template;
import com.journeyplanner.user.domain.exceptions.IllegalOperation;
import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import com.journeyplanner.user.domain.exceptions.UserWithEmailAlreadyExists;
import com.journeyplanner.user.domain.password.PasswordFacade;
import com.journeyplanner.user.infrastructure.input.request.*;
import com.journeyplanner.user.infrastructure.output.queue.MailSender;
import com.querydsl.core.types.Predicate;
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

    public void create(final CreateUserRequest request) {
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
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFound(format("Cannot found user with email {0}")));

        passwordFacade.generateAndSendResetPasswordLinkWithToken(user.getEmail(), user.getFirstName());
    }

    public void resetPassword(final ResetPasswordRequest request) {
        passwordFacade.validateToken(request.getToken(), request.getEmail());
        repository.updatePassword(request.getEmail(), passwordFacade.encodePassword(request.getNewPassword()));
    }

    public void block(final AddUserToBlacklistRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFound(format("Cannot found user with email {0}")));

        if (user.getRole().equals("ADMIN")) {
            throw new IllegalOperation("You cannot add to blacklist user with admin role");
        }

        repository.changeIsBlacklisted(request.getEmail(), Boolean.TRUE);

        mailSender.publish(SendMailEvent.builder()
                .to(user.getEmail())
                .templateName(Template.BLOCK_USER.getPath())
                .params(new HashMap<String, String>())
                .build());
    }

    public void unblock(final RemoveUserFromBlacklistRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFound(format("Cannot found user with email {0}")));

        repository.changeIsBlacklisted(request.getEmail(), Boolean.FALSE);

        mailSender.publish(SendMailEvent.builder()
                .to(user.getEmail())
                .templateName(Template.UNBLOCK_USER.getPath())
                .params(new HashMap<String, String>())
                .build());
    }

    public Page<UserDto> getAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable).map(UserDto::from);
    }
}
