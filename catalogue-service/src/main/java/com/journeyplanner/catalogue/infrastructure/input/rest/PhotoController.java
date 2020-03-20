package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.photo.PhotoDto;
import com.journeyplanner.catalogue.domain.photo.PhotoFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("photo")
@Slf4j
@AllArgsConstructor
public class PhotoController {

    private final PhotoFacade photoFacade;

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<byte[]> getById(@PathVariable String id, Model model) {

        PhotoDto photoDto = photoFacade.getById(id);
        return ResponseEntity.ok(photoDto.getImage().getData());
    }

    @GetMapping(value = "journey/{journeyId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getAllByJourneyId(@PathVariable String journeyId) {

        return ResponseEntity.ok(photoFacade.getAllForJourney(journeyId));
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> add(@RequestParam("journeyId") String journeyId, @RequestParam("image") MultipartFile file) {

        return ResponseEntity.ok(photoFacade.add(journeyId, file));
    }
}
