package pl.journeyplanner.gatewayservice.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class JwtProperties {

    @Value("${security.jwt.uri}")
    private String uri;

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.prefix}")
    private String prefix;

    @Value("${security.jwt.expiration-time}")
    private int expirationTime;
}
