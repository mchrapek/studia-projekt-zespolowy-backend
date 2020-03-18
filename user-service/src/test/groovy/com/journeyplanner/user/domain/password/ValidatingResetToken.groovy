package com.journeyplanner.user.domain.password

import com.journeyplanner.user.domain.exceptions.ResourceNotFound
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class ValidatingResetToken extends Specification {

    private PasswordFacade passwordFacade;
    private ResetTokenRepository resetTokenRepository;

    def setup() {
        resetTokenRepository = new ResetTokenRepositoryInMemory()

        passwordFacade = new PasswordFacade(resetTokenRepository, new ResetTokenCreator(), new BCryptPasswordEncoder())
    }

    def "token is valid should deprecate token in db"() {
        given:
        def email = "wayne@roo.com"
        passwordFacade.generateAndSendResetPasswordLinkWithToken(email)
        def resetToken = resetTokenRepository.findByEmail(email).first()

        when:
        passwordFacade.validateToken(resetToken.getToken(), resetToken.getEmail())

        then:
        resetTokenRepository.findByToken(resetToken.getToken()).get().active == Boolean.FALSE
    }

    def "token not exists should thrown exception"() {
        given:
        def email = "obi@wan.com"
        def token = UUID.randomUUID().toString()

        when:
        passwordFacade.validateToken(token, email)

        then:
        thrown ResourceNotFound
    }
}
