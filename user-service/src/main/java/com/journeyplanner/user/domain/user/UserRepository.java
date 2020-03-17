package com.journeyplanner.user.domain.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, String> {

    User save(User user);

    Optional<User> findByEmail(String email);
}
