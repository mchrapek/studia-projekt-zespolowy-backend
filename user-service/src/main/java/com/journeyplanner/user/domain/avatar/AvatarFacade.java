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

    public AvatarDto getByMail(final String mail) {
        return repository.findByMail(mail)
                .map(AvatarDto::from)
                .orElseThrow(() -> new ResourceNotFound(format("Cannot find avatar for : {0}", mail)));
    }

    public void add(final String mail, MultipartFile file) {
        Avatar updatedAvatar = repository.findByMail(mail)
                .map(a -> creator.from(a.getId(), mail, file))
                .orElseGet(() -> creator.from(mail, file))
                .orElseThrow(() -> new CannotParseFile(format("Cannot parse avatar for journey : {0} : ", mail)));

        repository.save(updatedAvatar);
    }
}
