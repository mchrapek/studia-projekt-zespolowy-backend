package com.journeyplanner.reservation.infrastructure.output;

import com.journeyplanner.reservation.domain.reservation.ReservationDto;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public void cancelReservation(@RequestHeader("x-username") String username, @PathVariable String id) {

        reservationFacade.cancelReservation(username, id);
    }
}
