package com.journeyplanner.user.domain.user;

interface UserCustomRepository {

    void updatePassword(String email, String password);

    void changeIsBlacklisted(final String email, final boolean isBlocked);

    void changeNewPasswordRequired(final String email, final boolean newPasswordRequired);
}
