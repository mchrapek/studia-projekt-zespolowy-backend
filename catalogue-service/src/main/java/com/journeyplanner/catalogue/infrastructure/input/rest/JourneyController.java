package com.journeyplanner.catalogue.infrastructure.input.rest;

import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyFacade;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateJourneyRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.CreateReservationRequest;
import com.journeyplanner.catalogue.infrastructure.input.request.UpdateJourneyRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class JourneyController {

    private final JourneyFacade journeyFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<JourneyDto>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "start", direction = Sort.Direction.DESC)) Pageable pageable) {

        return ResponseEntity.ok(journeyFacade.getAll(pageable));
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

    @PostMapping("reservation")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public void createReservation(@RequestBody @Valid CreateReservationRequest request,
                                  @RequestHeader("x-username") String username) {

        journeyFacade.createReservation(request, username);
    }
}
