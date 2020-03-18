package com.journeyplanner.user.domain.password

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class SendingResetPasswordTokenTest extends Specification {

    private PasswordFacade passwordFacade;
    private ResetTokenRepository resetTokenRepository;

    def setup() {
        resetTokenRepository = new ResetTokenRepositoryInMemory()

        passwordFacade = new PasswordFacade(resetTokenRepository, new ResetTokenCreator(), new BCryptPasswordEncoder())
    }

    def "should create reset password token"() {
        given:
        def email = "wayne@roo.com"

        when:
        passwordFacade.generateAndSendResetPasswordLinkWithToken(email)

        then:
        resetTokenRepository.findByEmail(email).size() == 1
    }
}
