package com.journeyplanner.common.config.security;

import lombok.Getter;

@Getter
public class JwtProperties {

    private final String uri;

    private final String secret;

    private final String header;

    private final String prefix;

    private final int expirationTime;

    public JwtProperties() {
        this.uri = "/auth/**";
        this.secret = "SuperSecretToken";
        this.header = "Authorization";
        this.prefix = "Bearer";
        this.expirationTime = 36000;
    }
}
