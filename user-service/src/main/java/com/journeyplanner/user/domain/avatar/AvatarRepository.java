package com.journeyplanner.user.domain.avatar;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AvatarRepository extends Repository<Avatar, String> {

    Optional<Avatar> findByEmail(String email);

    Avatar save(Avatar avatar);

    void deleteAll();
}
