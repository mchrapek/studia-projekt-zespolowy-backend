package com.journeyplanner.user.domain.avatar;

import com.journeyplanner.user.domain.exceptions.CannotParseFile;
import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class AvatarFacade {

    private final AvatarRepository repository;
    private final AvatarCreator creator;

    public AvatarDto getByEmail(final String email) {
        return repository.findByEmail(email)
                .map(AvatarDto::from)
                .orElseThrow(() -> new ResourceNotFound(format("Cannot find avatar for : {0}", email)));
    }

    public void add(final String email, final MultipartFile file) {
        Avatar updatedAvatar = repository.findByEmail(email)
                .map(a -> creator.from(a.getId(), email, file))
                .orElseGet(() -> creator.from(email, file))
                .orElseThrow(() -> new CannotParseFile(format("Cannot parse avatar for journey : {0} : ", email)));

        repository.save(updatedAvatar);
        log.info(format("Avatar for user {0} updated", email));
    }
}
