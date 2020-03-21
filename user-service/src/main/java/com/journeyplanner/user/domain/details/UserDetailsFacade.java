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

    public UserDetailsDto getDetailsByMail(final String mail) {
        return repository.findByEmail(mail)
                .map(UserDetailsDto::from)
                .orElseGet(UserDetailsDto::empty);
    }

    public UserDetailsDto getDetailsById(final String id) {
        return repository.findById(id)
                .map(UserDetailsDto::from)
                .orElseGet(UserDetailsDto::empty);
    }

    public UserDetailsDto addOrUpdateDetails(final String mail, final UpdateUserDetailsRequest request) {
        UserDetails updatedUserDetails = repository.findByEmail(mail)
                .map(d -> creator.createFrom(d, request))
                .orElseGet(() -> creator.createFrom(mail, request));

        return UserDetailsDto.from(repository.save(updatedUserDetails));
    }

    public AvatarDto getAvatarByMail(final String mail) {
        return avatarFacade.getByMail(mail);
    }

    public void addAvatar(final String mail, final MultipartFile file) {
        avatarFacade.add(mail, file);
    }
}
