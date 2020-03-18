package com.journeyplanner.user.domain.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, String>, UserCustomRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
