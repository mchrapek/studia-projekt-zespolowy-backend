package com.journeyplanner.catalogue.domain.photo;

import com.journeyplanner.catalogue.exceptions.CannotParseFile;
import com.journeyplanner.catalogue.exceptions.ResourcesNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
public class PhotoFacade {

    private final PhotoRepository photoRepository;
    private final PhotoCreator photoCreator;

    public String add(final String journeyId, final MultipartFile file) {
        Photo photo = photoCreator.from(journeyId, file)
                .orElseThrow(() -> new CannotParseFile(format("Cannot parse photo for journey : {0} : ", journeyId)));

        photoRepository.save(photo);
        log.info(format("Photo added to journey : {0}", journeyId));

        return photo.getId();
    }

    public PhotoDto getById(final String photoId) {
        return photoRepository.findById(photoId)
                .map(PhotoDto::from)
                .orElseThrow(() -> new ResourcesNotFound(format("Cannot found photo with id : {0} : ", photoId)));
    }

    public List<String> getAllForJourney(final String journeyId) {
        return photoRepository.findAllByJourneyId(journeyId)
                .stream()
                .map(Photo::getId)
                .collect(Collectors.toList());
    }
}
