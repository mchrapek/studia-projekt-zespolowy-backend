package com.journeyplanner.reservation.infrastructure.output;

import com.journeyplanner.reservation.domain.reservation.ReservationDto;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class ReservationController {

    public ReservationFacade reservationFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<ReservationDto>> getUserReservation(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(reservationFacade.getUserReservation(username));
    }
}
