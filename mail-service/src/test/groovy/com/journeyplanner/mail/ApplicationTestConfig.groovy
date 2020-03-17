package com.journeyplanner.mail

import com.journeyplanner.mail.app.MailServiceApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(basePackages = ["com.journeyplanner.mail"], excludeFilters = [
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MailServiceApplication.class)
])
class ApplicationTestConfig {
}
