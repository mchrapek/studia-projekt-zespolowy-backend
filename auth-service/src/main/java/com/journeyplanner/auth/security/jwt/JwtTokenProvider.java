package com.journeyplanner.auth.security.jwt;

import com.journeyplanner.auth.security.jwt.JwtProperties;
import com.journeyplanner.auth.user.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String createToken(final AppUser appUser) {
        long now = System.currentTimeMillis();

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", Collections.singletonList(appUser.getRole()));
        claims.put("id", appUser.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(appUser.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getExpirationTime() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret().getBytes())
                .compact();
    }
}
