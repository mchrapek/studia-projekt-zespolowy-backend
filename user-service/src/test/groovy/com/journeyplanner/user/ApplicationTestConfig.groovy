package com.journeyplanner.user

import com.journeyplanner.user.app.UserServiceApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(basePackages = ["com.journeyplanner.user"], excludeFilters = [
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserServiceApplication.class)
])
class ApplicationTestConfig {
}
