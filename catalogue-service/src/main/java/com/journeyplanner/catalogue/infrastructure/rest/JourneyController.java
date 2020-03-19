package com.journeyplanner.catalogue.infrastructure.rest;

import com.journeyplanner.catalogue.domain.journey.JourneyDto;
import com.journeyplanner.catalogue.domain.journey.JourneyFacade;
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

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class JourneyController {

    private final JourneyFacade journeyFacade;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<JourneyDto>> getPage(@PageableDefault @SortDefault.SortDefaults(
            @SortDefault(sort = "start", direction = Sort.Direction.DESC)) Pageable pageable) {

        return ResponseEntity.ok(journeyFacade.getAll(pageable));
    }
}
