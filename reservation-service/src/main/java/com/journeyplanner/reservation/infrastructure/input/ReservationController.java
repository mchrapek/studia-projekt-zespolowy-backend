package com.journeyplanner.reservation.infrastructure.input;

import com.journeyplanner.reservation.domain.reservation.ReservationDto;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
@Slf4j
@AllArgsConstructor
@Api(tags = "ReservationAPI")
public class ReservationController {

    public ReservationFacade reservationFacade;

    @GetMapping
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get User Reservations", notes = "User")
    public ResponseEntity<List<ReservationDto>> getUserReservation(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(reservationFacade.getUserReservation(username));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Cancel Reservation", notes = "User")
    public void cancelReservation(@RequestHeader("x-username") String username,
                                  @PathVariable("id") String paymentId) {

        reservationFacade.cancelByUser(username, paymentId);
    }
}
