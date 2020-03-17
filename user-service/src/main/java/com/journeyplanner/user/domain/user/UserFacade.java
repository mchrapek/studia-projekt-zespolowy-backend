package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.infrastructure.request.CreateAppUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class UserFacade {

    private UserRepository userRepository;

    private UserCreator userCreator;

    public void createUser(CreateAppUserRequest request) {
        // TODO check user email
        User userToSave = userCreator.from(request);
        userRepository.save(userToSave);
        // TODO send email
    }
}
