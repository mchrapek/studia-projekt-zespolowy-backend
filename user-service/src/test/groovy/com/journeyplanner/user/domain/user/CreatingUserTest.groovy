package com.journeyplanner.user.domain.user

import com.journeyplanner.user.domain.exceptions.UserWithEmailAlreadyExists
import com.journeyplanner.user.domain.password.PasswordFacade
import com.journeyplanner.user.infrastructure.request.CreateUserRequest
import spock.lang.Specification

class CreatingUserTest extends Specification {

    private UserFacade userFacade;
    private UserRepository userRepository;
    private PasswordFacade passwordFacade;

    def setup() {
        userRepository = new UserRepositoryInMemory()
        passwordFacade = Mock(PasswordFacade.class)

        userFacade = new UserFacade(userRepository, new UserCreator(), passwordFacade)
    }

    def "should create new user"() {
        given:
        def email = "norman@jayden.com"
        def request = new CreateUserRequest(email, "12345a", "Norman", "Jayden")

        when:
        userFacade.createUser(request)

        then:
        userRepository.findByEmail(email).isPresent()

        then:
        1 * passwordFacade.encodePassword("12345a") >> "hashedPasswordHere"
    }

    def "email already exists should throw exception"() {
        given:
        def email = "james@bond.com"
        def request = new CreateUserRequest(email, "12345a", "James", "Bond")

        when:
        2.times { userFacade.createUser(request) }

        then:
        thrown UserWithEmailAlreadyExists

        then:
        1 * passwordFacade.encodePassword("12345a") >> "hashedPasswordHere"
    }
}
