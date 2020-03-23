package com.journeyplanner.payment.domain.account

import com.journeyplanner.common.config.events.TransferType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class ReturnTransactionSpec extends Specification {

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

    def "should create load transaction"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email)
        accountRepository.save(account)

        and:
        def transfer = TransferMotherObject.aTransfer(email, TransferType.RETURN)
        transferRepository.save(transfer)

        when:
        transferScheduler.fetch()

        then:
        transferRepository.findById(transfer.getId()).get().status == TransferStatus.DONE
        accountHistoryRepository.findAllByAccountId(account.getId()).size() == 1
        accountRepository.findByEmail(email).get().balance == BigDecimal.TEN
    }

    def "should load older transaction"() {
        given:
        def email = "frodo@baggins.com"
        def account = AccountMotherObject.aAccount(email)
        accountRepository.save(account)

        and:
        def transfer1 = TransferMotherObject.aTransfer(email, TransferType.RETURN)
        transferRepository.save(transfer1)

        and:
        def transfer2 = TransferMotherObject.aTransfer(email, TransferType.RETURN,
                new BigDecimal(20.00), Instant.now().minus(10, ChronoUnit.MINUTES))
        transferRepository.save(transfer2)

        when:
        transferScheduler.fetch()

        then:
        transferRepository.findById(transfer1.getId()).get().status == TransferStatus.PENDING
        transferRepository.findById(transfer2.getId()).get().status == TransferStatus.DONE
        accountHistoryRepository.findAllByAccountId(account.getId()).size() == 1
        accountRepository.findByEmail(email).get().balance == new BigDecimal(20.00)
    }
}

