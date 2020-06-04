package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.journey.Journey;
import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyFacade;
import com.journeyplanner.catalogue.domain.photo.PhotoFacade;
import com.journeyplanner.catalogue.infrastructure.input.request.AddGuideToJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.response.JourneyWithPhotoResponse;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue/journeys")
@Slf4j
@AllArgsConstructor
@Api(tags = "JourneyAPI")
public class JourneyController {

    private final JourneyFacade journeyFacade;
    private final PhotoFacade photoFacade;

    @GetMapping
    @ApiOperation(value = "Get pageable Journeys", notes = "Anonymous")
    public ResponseEntity<Page<JourneyWithPhotoResponse>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "start", direction = Sort.Direction.DESC)) Pageable pageable, @QuerydslPredicate(root = Journey.class) Predicate predicate) {

        return ResponseEntity.ok(
                journeyFacade.getAll(predicate, pageable)
                        .map(e -> JourneyWithPhotoResponse.from(e, photoFacade.getAllForJourney(e.getId())))
        );
    }

    @PostMapping
    @ApiOperation(value = "Create Journey", notes = "Admin")
    public ResponseEntity<JourneyWithPhotoResponse> create(@RequestBody @Valid CreateJourneyRequest request) {

        JourneyDto journeyDto = journeyFacade.create(request);

        return ResponseEntity.ok(
                JourneyWithPhotoResponse.from(journeyDto, photoFacade.getAllForJourney(journeyDto.getId()))
        );
    }

    @PutMapping("{journeyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Journey", notes = "Admin")
    public ResponseEntity<JourneyWithPhotoResponse> update(@PathVariable("journeyId") String journeyId,
                                                           @RequestBody @Valid UpdateJourneyRequest request) {

        JourneyDto journeyDto = journeyFacade.update(journeyId, request);

        return ResponseEntity.ok(
                JourneyWithPhotoResponse.from(journeyDto, photoFacade.getAllForJourney(journeyDto.getId()))
        );
    }

    @DeleteMapping(value = "{journeyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Cancel Journey", notes = "Admin")
    public void cancel(@PathVariable("journeyId") String journeyId) {

        journeyFacade.cancel(journeyId);
    }

    @PostMapping("{journeyId}/reservation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Create Journey reservation", notes = "User")
    public void createReservation(@PathVariable("journeyId") String journeyId,
                                  @RequestHeader("x-username") String username) {

        journeyFacade.createReservation(journeyId, username);
    }

    @GetMapping(value = "{journeyId}/photos")
    @ApiOperation(value = "Get all id photos for Journey", notes = "Anonymous")
    public ResponseEntity<List<String>> getAllByJourneyId(@PathVariable("journeyId") String journeyId) {

        return ResponseEntity.ok(photoFacade.getAllForJourney(journeyId));
    }

    @PostMapping(value = "{journeyId}/photos")
    @ApiOperation(value = "Add photo to Journey", notes = "Admin")
    public ResponseEntity<String> addPhoto(@PathVariable("journeyId") String journeyId, @RequestParam("image") MultipartFile file) {

        return ResponseEntity.ok(photoFacade.add(journeyId, file));
    }

    @PutMapping("/{journeyId}/guides")
    @ApiOperation(value = "Add Guide to Journey", notes = "Admin")
    public ResponseEntity<JourneyWithPhotoResponse> addGuideToJourney(@PathVariable("journeyId") String journeyId,
                                                                      @RequestBody @Valid AddGuideToJourneyRequest request) {

        JourneyDto journeyDto = journeyFacade.addGuide(journeyId, request);

        return ResponseEntity.ok(
                JourneyWithPhotoResponse.from(journeyDto, photoFacade.getAllForJourney(journeyDto.getId()))
        );
    }

    @GetMapping("guides")
    @ApiOperation(value = "Get Guide Journeys", notes = "Guide")
    public ResponseEntity<List<JourneyWithPhotoResponse>> getGuideJourneys(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(
                journeyFacade.getGuideJourneys(username).stream()
                        .map(e -> JourneyWithPhotoResponse.from(e, photoFacade.getAllForJourney(e.getId())))
                        .collect(Collectors.toList())
        );
    }
}
