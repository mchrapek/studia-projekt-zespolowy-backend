package com.journeyplanner.payment.domain.account

import com.journeyplanner.common.config.events.TransferType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class GetTransactionSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private AccountRepository accountRepository

    @Autowired
    private AccountHistoryRepository accountHistoryRepository

    @Autowired
    private TransferRepository transferRepository

    @Autowired
    private TransferScheduler transferScheduler

    def setup() {
        accountRepository.deleteAll()
        accountHistoryRepository.deleteAll()
        transferRepository.deleteAll()
    }

    def "should get transaction details"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email, BigDecimal.TEN)
        accountRepository.save(account)

        and:
        def transfer = TransferMotherObject.aTransfer(email, TransferType.LOAD, BigDecimal.TEN)
        transferRepository.save(transfer)

        and:
        transferScheduler.fetch()

        when:
        def result = mvc.perform(get("/payment/" + transfer.getPaymentId())
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(transfer.getPaymentId())
    }

    def "should get newest details"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email, BigDecimal.TEN)
        accountRepository.save(account)

        and:
        def transfer1 = Transfer.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .paymentId(UUID.randomUUID().toString())
                .value(BigDecimal.ONE)
                .type(TransferType.RETURN)
                .status(TransferStatus.ERROR)
                .eventTime(Instant.now())
                .build()
        transferRepository.save(transfer1)

        and:
        def transfer2 = Transfer.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .paymentId(transfer1.getPaymentId())
                .value(BigDecimal.ONE)
                .type(TransferType.RETURN)
                .status(TransferStatus.DONE)
                .eventTime(Instant.now())
                .build()
        transferRepository.save(transfer2)

        when:
        def result = mvc.perform(get("/payment/" + transfer2.getPaymentId())
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        then:
        result.response.getContentAsString().contains(transfer2.getPaymentId())
    }
}
