package com.journeyplanner.user.domain.details;

import com.journeyplanner.user.domain.avatar.AvatarDto;
import com.journeyplanner.user.domain.avatar.AvatarFacade;
import com.journeyplanner.user.infrastructure.input.request.UpdateUserDetailsRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@AllArgsConstructor
public class UserDetailsFacade {

    private final UserDetailsRepository repository;
    private final UserDetailsCreator creator;
    private final AvatarFacade avatarFacade;

    public UserDetailsDto getDetailsByEmail(final String email) {
        return repository.findByEmail(email)
                .map(UserDetailsDto::from)
                .orElseGet(UserDetailsDto::empty);
    }

    public UserDetailsDto addOrUpdateDetails(final String email, final UpdateUserDetailsRequest request) {
        UserDetails updatedUserDetails = repository.findByEmail(email)
                .map(d -> creator.createFrom(d, request))
                .orElseGet(() -> creator.createFrom(email, request));

        return UserDetailsDto.from(repository.save(updatedUserDetails));
    }

    public AvatarDto getAvatarByEmail(final String email) {
        return avatarFacade.getByEmail(email);
    }

    public void addAvatar(final String email, final MultipartFile file) {
        avatarFacade.add(email, file);
    }
}
