package com.journeyplanner.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journeyplanner.auth.security.jwt.JwtProperties;
import com.journeyplanner.auth.security.jwt.JwtTokenProvider;
import com.journeyplanner.auth.security.dao.UserCredentialsRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.jwtTokenProvider = jwtTokenProvider;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtProperties.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserCredentialsRequest credentials =
                    new ObjectMapper().readValue(request.getInputStream(), UserCredentialsRequest.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), Collections.emptyList());

            credentials.setPassword("");

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // TODO get user id for claims

        response.addHeader(jwtProperties.getHeader(),
                jwtProperties.getPrefix() + " " + jwtTokenProvider.createToken(authResult));
    }
}
