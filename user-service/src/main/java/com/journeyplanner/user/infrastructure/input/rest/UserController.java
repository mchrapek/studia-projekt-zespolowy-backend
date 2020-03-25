package com.journeyplanner.user.infrastructure.input.rest;

import com.journeyplanner.user.domain.avatar.AvatarDto;
import com.journeyplanner.user.domain.details.UserDetailsDto;
import com.journeyplanner.user.domain.details.UserDetailsFacade;
import com.journeyplanner.user.domain.user.GuideDto;
import com.journeyplanner.user.domain.user.User;
import com.journeyplanner.user.domain.user.UserDto;
import com.journeyplanner.user.domain.user.UserFacade;
import com.journeyplanner.user.infrastructure.input.request.*;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
@AllArgsConstructor
@Api(tags = "UserAPI")
public class UserController {

    private final UserFacade userFacade;
    private final UserDetailsFacade userDetailsFacade;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Register New User")
    public void createUser(@RequestBody @Valid CreateUserRequest request) {

        userFacade.create(request);
    }

    @PostMapping("reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Send Email With Reset Password Token")
    public void generateResetPasswordLink(@RequestBody @Valid GenerateResetPasswordLinkRequest request) {

        userFacade.sendResetPasswordToken(request);
    }

    @PostMapping("password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Reset Password")
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest request) {

        userFacade.resetPassword(request);
    }

    @PostMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Add User to blacklist")
    public void addUserToBlacklist(@RequestBody @Valid AddUserToBlacklistRequest request) {

        userFacade.block(request);
    }

    @DeleteMapping("block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Remove User from blacklist")
    public void removeUserFromBlacklist(@RequestBody @Valid RemoveUserFromBlacklistRequest request) {

        userFacade.unblock(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get all Users")
    public ResponseEntity<Page<UserDto>> getUsers(@PageableDefault @SortDefault.SortDefaults(@SortDefault(sort = "email", direction = Sort.Direction.DESC))
                                                              Pageable pageable, @QuerydslPredicate(root = User.class) Predicate predicate) {

        return ResponseEntity.ok(userFacade.getAll(predicate, pageable));
    }

    @GetMapping("details")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get User Details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(userDetailsFacade.getDetailsByEmail(username));
    }

    @PostMapping("details")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Update User Details")
    public ResponseEntity<UserDetailsDto> updateUserDetails(@RequestHeader("x-username") String username,
                                                            @RequestBody @Valid UpdateUserDetailsRequest request) {

        return ResponseEntity.ok(userDetailsFacade.addOrUpdateDetails(username, request));
    }

    @GetMapping("details/{id}")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get User Details By Id")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable("id") String userId) {

        return ResponseEntity.ok(userDetailsFacade.getDetailsById(userId));
    }

    @GetMapping(value = "avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get User Avatar")
    public ResponseEntity<byte[]> getAvatarForUser(@RequestHeader("x-username") String username) {

        AvatarDto avatarDto = userDetailsFacade.getAvatarByEmail(username);
        return ResponseEntity.ok(avatarDto.getImage().getData());
    }

    @PostMapping(value = "avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Add/Update User Avatar")
    public void add(@RequestHeader("x-username") String username, @RequestParam("image") MultipartFile file) {

        userDetailsFacade.addAvatar(username, file);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get Guides")
    public ResponseEntity<List<GuideDto>> guides() {

        return ResponseEntity.ok(userFacade.getGuides());
    }

    @PostMapping("register/guides")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Add new guide")
    public void createGuide(@RequestBody @Valid CreateGuideRequest request) {

        userFacade.createGuide(request);
    }
}
