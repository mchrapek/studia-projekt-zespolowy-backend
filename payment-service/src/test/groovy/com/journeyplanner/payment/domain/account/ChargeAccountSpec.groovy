package com.journeyplanner.payment.domain.account

import com.fasterxml.jackson.databind.ObjectMapper
import com.journeyplanner.payment.domain.account.AccountRepository
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class ChargeAccountSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AccountRepository accountRepository

    def setup() {
        accountRepository.deleteAll()
    }

    def "should charge account"() {
        given:
        def email = "james@bond.com"
        def account = AccountMotherObject.aAccount(email)
        accountRepository.save(account)

        and:
        def request = new ChargeAccountRequest(BigDecimal.TEN)
        def json = new ObjectMapper().writeValueAsString(request)

        when:
        mvc.perform(post("/billing/accounts/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-username", email)
                .content(json))
                .andExpect(status().isOk())
                .andReturn()

        then:
        accountRepository.findByEmail(email).get().balance == BigDecimal.TEN
    }
}
