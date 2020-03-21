package com.journeyplanner.user.domain.avatar;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AvatarRepository extends Repository<Avatar, String> {

    Optional<Avatar> findByMail(String mail);

    Avatar save(Avatar avatar);

    void deleteAll();
}
