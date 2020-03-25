package com.journeyplanner.payment.domain.account

import com.journeyplanner.common.config.events.TransferType
import com.journeyplanner.payment.exceptions.IllegalOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class LoadTransactionSpec extends Specification {

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

    def "should create return transaction"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email, new BigDecimal(10.00))
        accountRepository.save(account)

        and:
        def transfer = TransferMotherObject.aTransfer(email, TransferType.LOAD, new BigDecimal(8.00))
        transferRepository.save(transfer)

        when:
        transferScheduler.fetch()

        then:
        transferRepository.findById(transfer.getId()).get().status == TransferStatus.DONE
        accountHistoryRepository.findAllByAccountId(account.getId()).size() == 1
        accountRepository.findByEmail(email).get().balance == new BigDecimal(2.00)
    }

    def "should fail when user doesn't have enough money"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email, new BigDecimal(10.00))
        accountRepository.save(account)

        and:
        def transfer = TransferMotherObject.aTransfer(email, TransferType.LOAD, new BigDecimal(18.00))
        transferRepository.save(transfer)

        when:
        transferScheduler.fetch()

        then:
        transferRepository.findById(transfer.getId()).get().status == TransferStatus.ERROR
    }
}
