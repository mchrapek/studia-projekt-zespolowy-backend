package com.journeyplanner.reservation.domain.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserFetcher {

    private final RestTemplate restTemplate;
    private final String userUri;

    public UserFetcher(@Value("${user.uri}") String userUri, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.userUri = userUri;
    }

    public BasicInfoUser fetch(final String email) {
        return restTemplate.getForObject(userUri + "basic?email=" + email, BasicInfoUser.class);
    }
}
