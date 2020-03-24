package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.journey.Journey;
import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyFacade;
import com.journeyplanner.catalogue.domain.photo.PhotoFacade;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateReservationRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import com.querydsl.core.types.Predicate;
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
@RequestMapping
@Slf4j
@AllArgsConstructor
public class JourneyController {

    private final JourneyFacade journeyFacade;
    private final PhotoFacade photoFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<JourneyDto>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "start", direction = Sort.Direction.DESC)) Pageable pageable, @QuerydslPredicate(root = Journey.class) Predicate predicate) {

        return ResponseEntity.ok(journeyFacade.getAll(predicate, pageable));
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<JourneyDto> create(@RequestBody @Valid CreateJourneyRequest request) {

        return ResponseEntity.ok(journeyFacade.create(request));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public ResponseEntity<JourneyDto> update(@RequestBody @Valid UpdateJourneyRequest request) {

        return ResponseEntity.ok(journeyFacade.update(request));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void cancel(@PathVariable String id) {

        journeyFacade.cancel(id);
    }

    @PostMapping("reservation")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void createReservation(@RequestBody @Valid CreateReservationRequest request,
                                  @RequestHeader("x-username") String username) {

        journeyFacade.createReservation(request, username);
    }

    @GetMapping(value = "journey/{journeyId}/photo")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getAllByJourneyId(@PathVariable String journeyId) {

        return ResponseEntity.ok(photoFacade.getAllForJourney(journeyId));
    }

    @PostMapping(value = "journey/{journeyId}/photo")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> add(@PathVariable("journeyId") String journeyId, @RequestParam("image") MultipartFile file) {

        return ResponseEntity.ok(photoFacade.add(journeyId, file));
    }
}
