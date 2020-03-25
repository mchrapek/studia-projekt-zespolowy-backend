package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.photo.PhotoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogue/photos")
@Slf4j
@AllArgsConstructor
@Api(tags = "PhotoAPI")
public class PhotoController {

    private final PhotoFacade photoFacade;

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get photo by id", notes = "Anonymous")
    public ResponseEntity<byte[]> getById(@PathVariable("id") String photoId) {

        return ResponseEntity.ok(photoFacade.getById(photoId).getImage().getData());
    }
}
