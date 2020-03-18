package com.journeyplanner.auth.security.jwt;

import com.journeyplanner.auth.security.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String createToken(final Authentication auth) {
        long now = System.currentTimeMillis();

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("id", UUID.randomUUID().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getExpirationTime() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret().getBytes())
                .compact();
    }
}
