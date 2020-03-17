package com.journeyplanner.mail.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AdditionalRepositoryMethodsTest extends Specification {

    @Autowired
    private MailService service

    @Autowired
    private MailRepository repository

    def setup() {
        repository.deleteAll()
    }

    def "should find pending mail and change status to update"() {
        given:
        2.times { repository.save(MailMotherObject.aMail(UUID.randomUUID().toString(), MailStatus.PENDING)) }
        5.times { repository.save(MailMotherObject.aMail(UUID.randomUUID().toString(), MailStatus.ERROR)) }

        when:
        def mail = service.getPendingMessageAndUpdateToProcessingStatus()

        then:
        def processingMail = repository.findById(mail.get().getId())
        processingMail.get().getStatus() == MailStatus.PROCESSING
    }

    def "should update mail status"() {
        given:
        def mailId = UUID.randomUUID().toString()
        def mail = MailMotherObject.aMail(mailId, MailStatus.PROCESSING)
        repository.save(mail)

        when:
        service.updateStatus(mailId, MailStatus.SENT)

        then:
        def updateMail = repository.findById(mailId)
        updateMail.get().getStatus() == MailStatus.SENT
    }
}
