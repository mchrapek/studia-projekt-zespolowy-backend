package com.journeyplanner.user.infrastructure;

import com.journeyplanner.user.domain.user.UserFacade;
import com.journeyplanner.user.domain.resettoken.ResetTokenFacade;
import com.journeyplanner.user.infrastructure.request.CreateAppUserRequest;
import com.journeyplanner.user.infrastructure.request.ResetPasswordLinkRequest;
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
    private final ResetTokenFacade resetTokenFacade;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void createAppUser(@RequestBody @Valid CreateAppUserRequest request) {

        userFacade.createUser(request);
    }

    @PostMapping("reset")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void generateResetPasswordLink(@RequestBody @Valid ResetPasswordLinkRequest request) {

        resetTokenFacade.generateAndSendResetPasswordLinkWithToken(request);
    }
}
