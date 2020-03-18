package pl.journeyplanner.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import pl.journeyplanner.gatewayservice.security.AuthenticationZuulFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableWebSecurity
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public AuthenticationZuulFilter simpleFilter() {
        return new AuthenticationZuulFilter();
    }
}
