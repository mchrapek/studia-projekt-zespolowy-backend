package com.journeyplanner.gateway.security;

import com.journeyplanner.common.config.paths.Paths;
import com.journeyplanner.common.config.security.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    private final JwtProperties jwtProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtProperties), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST, jwtProperties.getUri()).permitAll()

                .antMatchers(HttpMethod.GET, Paths.GET_PERMIT_ALL_PATHS).permitAll()
                .antMatchers(HttpMethod.POST, Paths.POST_PERMIT_ALL_PATHS).permitAll()
                .antMatchers(HttpMethod.PUT, Paths.PUT_PERMIT_ALL_PATHS).permitAll()

                .antMatchers(HttpMethod.GET, Paths.GET_USER_PATHS).hasRole("USER")
                .antMatchers(HttpMethod.POST, Paths.POST_USER_PATHS).hasRole("USER")
                .antMatchers(HttpMethod.DELETE, Paths.DELETE_USER_PATHS).hasRole("USER")

                .antMatchers(HttpMethod.GET, Paths.GET_ADMIN_PATHS).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, Paths.POST_ADMIN_PATHS).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, Paths.PUT_ADMIN_PATHS).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, Paths.DELETE_ADMIN_PATHS).hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, Paths.GET_GUIDE_PATHS).hasRole("GUIDE")
                .antMatchers(HttpMethod.OPTIONS).permitAll()

                .anyRequest().authenticated();
    }
}
