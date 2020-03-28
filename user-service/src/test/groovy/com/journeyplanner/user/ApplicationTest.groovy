package com.journeyplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
@DirtiesContext
class ApplicationTest extends Specification {

    @Autowired
    private WebApplicationContext context

    def "should boot up without errors"() {
        expect: "web application context exists"
        context != null
    }
}
