package com.journeyplanner.payment.domain.account

import com.journeyplanner.common.config.events.TransferType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class RetryTransactionSpec extends Specification {

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

    def "should get newest transaction details"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email, BigDecimal.TEN)
        accountRepository.save(account)

        and:
        def transfer1 = TransferMotherObject.aTransfer(email, TransferType.LOAD, new BigDecimal(20.00))
        transferRepository.save(transfer1)
        transferScheduler.fetch()

        and:
        def transfer2 = TransferMotherObject.aTransfer(email, TransferType.RETURN, new BigDecimal(30.00))
        transferRepository.save(transfer2)
        transferScheduler.fetch()

        when:
        mvc.perform(post("/payment/" + transfer1.getPaymentId())
                .header("x-username", email))
                .andExpect(status().isOk())
                .andReturn()

        and:
        transferScheduler.fetch()

        then:
        transferRepository.findFirstByPaymentIdOrderByEventTimeDesc(transfer1.getPaymentId()).get().status == TransferStatus.DONE
        transferRepository.findFirstByPaymentIdOrderByEventTimeDesc(transfer2.getPaymentId()).get().status == TransferStatus.DONE

        and:
        transferRepository.findById(transfer1.getId()).get().status == TransferStatus.ERROR
        transferRepository.findById(transfer2.getId()).get().status == TransferStatus.DONE
    }
}
