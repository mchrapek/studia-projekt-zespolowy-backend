package com.journeyplanner.user.infrastructure.rest;

import com.journeyplanner.user.domain.user.UserFacade;
import com.journeyplanner.user.infrastructure.request.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void createAppUser(@RequestBody @Valid CreateUserRequest request) {

        userFacade.createUser(request);
    }

    @PostMapping("reset")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void generateResetPasswordLink(@RequestBody @Valid GenerateResetPasswordLinkRequest request) {

        userFacade.sendResetPasswordToken(request);
    }

    @PostMapping("password")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest request) {

        userFacade.changePassword(request);
    }

    @PostMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void addUserToBlacklist(@RequestBody @Valid AddUserToBlacklistRequest request) {

        userFacade.blockUser(request);
    }

    @DeleteMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void removeUserFromBlacklist(@RequestBody @Valid RemoveUserFromBlacklistRequest request) {

        userFacade.unblockedUser(request);
    }
}
