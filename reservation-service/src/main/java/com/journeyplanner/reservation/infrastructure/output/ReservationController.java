package com.journeyplanner.reservation.infrastructure.output;

import com.journeyplanner.reservation.domain.reservation.ReservationDto;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class ReservationController {

    public ReservationFacade reservationFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<ReservationDto> getUserReservation() {

        return ResponseEntity.ok(null);
    }
}
