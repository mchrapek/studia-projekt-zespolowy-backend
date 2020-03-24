package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.journey.Journey;
import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyFacade;
import com.journeyplanner.catalogue.domain.photo.PhotoFacade;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
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

@RestController
@RequestMapping("/catalogue/journeys")
@Slf4j
@AllArgsConstructor
@Api(tags = "JourneyAPI")
public class JourneyController {

    private final JourneyFacade journeyFacade;
    private final PhotoFacade photoFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get pageable Journeys")
    public ResponseEntity<Page<JourneyDto>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "start", direction = Sort.Direction.DESC)) Pageable pageable, @QuerydslPredicate(root = Journey.class) Predicate predicate) {

        return ResponseEntity.ok(journeyFacade.getAll(predicate, pageable));
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Create Journey")
    public ResponseEntity<JourneyDto> create(@RequestBody @Valid CreateJourneyRequest request) {

        return ResponseEntity.ok(journeyFacade.create(request));
    }

    @PutMapping("{id}")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Update Journey")
    public ResponseEntity<JourneyDto> update(@PathVariable("id") String journeyId, @RequestBody @Valid UpdateJourneyRequest request) {

        return ResponseEntity.ok(journeyFacade.update(journeyId, request));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Cancel Journey")
    public void cancel(@PathVariable("id") String journeyId) {

        journeyFacade.cancel(journeyId);
    }

    @PostMapping("{id}/reservations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Create Journey reservation")
    public void createReservation(@PathVariable("id") String journeyId, @RequestHeader("x-username") String username) {

        journeyFacade.createReservation(journeyId, username);
    }

    @GetMapping(value = "{id}/photos")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get all id photos for Journey")
    public ResponseEntity<List<String>> getAllByJourneyId(@PathVariable("id") String journeyId) {

        return ResponseEntity.ok(photoFacade.getAllForJourney(journeyId));
    }

    @PostMapping(value = "{id}/photos")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Add photo to Journey")
    public ResponseEntity<String> add(@PathVariable("id") String journeyId, @RequestParam("image") MultipartFile file) {

        return ResponseEntity.ok(photoFacade.add(journeyId, file));
    }
}
