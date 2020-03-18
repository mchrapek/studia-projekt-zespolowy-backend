package com.journeyplanner.auth.security;

import com.journeyplanner.auth.security.dto.UserCredentialsRequest;
import com.journeyplanner.auth.security.exceptions.UserIsBlocked;
import com.journeyplanner.auth.user.AppUserService;
import com.journeyplanner.auth.user.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userByEmail = appUserService.getUserByEmail(username);
        AppUser appUser = userByEmail
                .orElseThrow(() -> new UsernameNotFoundException(format("User with email : {0} doesn't exists", username)));

        if (appUser.isBlocked()) {
            throw new UserIsBlocked(format("User with email : {0} is blocked by administrator", username));
        }

        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());

        return new User(appUser.getEmail(), appUser.getPassword(), grantedAuthorities);
    }
}
