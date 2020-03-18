package pl.journeyplanner.gatewayservice.security;

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
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/user/reset").permitAll()
                .antMatchers(HttpMethod.GET, "/user/info").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/user/block").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/block").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
