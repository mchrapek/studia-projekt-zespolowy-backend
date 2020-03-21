package com.journeyplanner.user.domain.details;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserDetailsRepository extends Repository<UserDetails, String> {

    Optional<UserDetails> findByEmail(String email);

    UserDetails save(UserDetails userDetails);

    void deleteAll();
}
