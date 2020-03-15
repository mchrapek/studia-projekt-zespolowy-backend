package com.journeyplanner.auth.user;

import com.journeyplanner.auth.user.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    public Optional<AppUser> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
