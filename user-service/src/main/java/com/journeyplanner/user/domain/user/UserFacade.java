package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.domain.exceptions.UserWithEmailAlreadyExists;
import com.journeyplanner.user.domain.password.PasswordFacade;
import com.journeyplanner.user.infrastructure.request.CreateUserRequest;
import com.journeyplanner.user.infrastructure.request.GenerateResetPasswordLinkRequest;
import com.journeyplanner.user.infrastructure.request.ResetPasswordRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class UserFacade {

    private UserRepository repository;
    private UserCreator userCreator;
    private PasswordFacade passwordFacade;

    public void createUser(final CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExists(format("User with email {0} already exists", request.getEmail()));
        }

        User userToSave = userCreator.from(request, passwordFacade.encodePassword(request.getPassword()));
        repository.save(userToSave);
        // TODO send email
    }

    public void sendResetPasswordToken(final GenerateResetPasswordLinkRequest request) {
        passwordFacade.generateAndSendResetPasswordLinkWithToken(request.getEmail());
    }

    public void changePassword(final ResetPasswordRequest request) {
        passwordFacade.validateToken(request.getToken(), request.getEmail());
        repository.updatePassword(request.getEmail(), passwordFacade.encodePassword(request.getNewPassword()));
    }
}
