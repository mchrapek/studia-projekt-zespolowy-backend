package com.journeyplanner.user.infrastructure.input.rest;

import com.journeyplanner.user.domain.user.UserDto;
import com.journeyplanner.user.domain.user.UserFacade;
import com.journeyplanner.user.infrastructure.input.request.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        userFacade.resetPassword(request);
    }

    @PostMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void addUserToBlacklist(@RequestBody @Valid AddUserToBlacklistRequest request) {

        userFacade.block(request);
    }

    @DeleteMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void removeUserFromBlacklist(@RequestBody @Valid RemoveUserFromBlacklistRequest request) {

        userFacade.unblock(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<UserDto>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "email", direction = Sort.Direction.DESC)) Pageable pageable) {

        return ResponseEntity.ok(userFacade.getAll(pageable));
    }
}
