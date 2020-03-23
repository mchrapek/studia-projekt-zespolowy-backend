package com.journeyplanner.payment.domain.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class GetAccountSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AccountRepository accountRepository

    def setup() {
        accountRepository.deleteAll()
    }

    def "should return account"() {
        given:
        def email = "james@bond.com"
        def account = AccountMotherObject.aAccount(email)
        accountRepository.save(account)

        when:
        def result = mvc.perform(get("/account")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(account.getId())
    }

    def "should create account if account doesn't exists"() {
        given:
        def email = "james@bond.com"

        when:
        def result = mvc.perform(get("/account")
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        accountRepository.findByEmail(email).get().email == email
    }
}
