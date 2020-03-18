package com.journeyplanner.user.infrastructure.rest;

import com.journeyplanner.user.domain.user.UserFacade;
import com.journeyplanner.user.infrastructure.request.CreateUserRequest;
import com.journeyplanner.user.infrastructure.request.GenerateResetPasswordLinkRequest;
import com.journeyplanner.user.infrastructure.request.ResetPasswordRequest;
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
        // TODO handle rest exception
        userFacade.changePassword(request);
    }
}
