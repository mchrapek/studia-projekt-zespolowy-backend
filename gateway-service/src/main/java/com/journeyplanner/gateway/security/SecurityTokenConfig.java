package com.journeyplanner.gateway.security;

import com.journeyplanner.common.config.paths.Paths;
import com.journeyplanner.common.config.security.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    private JwtProperties jwtProperties;

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

                .antMatchers(HttpMethod.POST, jwtProperties.getUri()).permitAll()
                .antMatchers(HttpMethod.POST, Paths.POST_PERMIT_ALL_PATHS).permitAll()

                .antMatchers(HttpMethod.GET, Paths.GET_USER_PATHS).hasRole("USER")
                .antMatchers(HttpMethod.POST, Paths.POST_USER_PATHS).hasRole("USER")

                .antMatchers(HttpMethod.GET, Paths.GET_ADMIN_PATHS).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, Paths.POST_ADMIN_PATHS).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, Paths.DELETE_ADMIN_PATHS).hasRole("ADMIN")

                .anyRequest().authenticated();
    }
}
