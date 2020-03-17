package com.journeyplanner.mail.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

interface MailRepository extends MongoRepository<Mail, String>, CustomMailRepository {
}
