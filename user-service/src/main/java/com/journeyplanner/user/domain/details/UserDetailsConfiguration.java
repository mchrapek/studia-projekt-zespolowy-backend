package com.journeyplanner.user.domain.details;

import com.journeyplanner.user.domain.avatar.AvatarFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDetailsConfiguration {

    @Bean
    UserDetailsFacade userDetailsFacade(UserDetailsRepository userDetailsRepository, AvatarFacade avatarFacade) {
        return new UserDetailsFacade(userDetailsRepository, new UserDetailsCreator(), avatarFacade);
    }
}
