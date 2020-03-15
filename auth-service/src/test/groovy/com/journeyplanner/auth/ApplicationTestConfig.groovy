package com.journeyplanner.auth

import com.journeyplanner.auth.app.AuthServiceApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(basePackages = ["com.journeyplanner.auth"], excludeFilters = [
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuthServiceApplication.class)
])
class ApplicationTestConfig {
}
