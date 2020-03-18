package com.journeyplanner.user.domain.user;

interface UserCustomRepository {

    void updatePassword(String email, String password);
}
