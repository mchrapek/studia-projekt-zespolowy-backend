package com.journeyplanner.reservation.infrastructure.input;

import com.journeyplanner.reservation.domain.reservation.ReservationDto;
import com.journeyplanner.reservation.domain.reservation.ReservationFacade;
import com.journeyplanner.reservation.domain.users.BasicInfoUser;
import com.journeyplanner.reservation.domain.users.UserFetcher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reservations")
@Slf4j
@AllArgsConstructor
@Api(tags = "ReservationAPI")
public class ReservationController {

    public ReservationFacade reservationFacade;
    public UserFetcher userFetcher;

    @GetMapping
    @ApiOperation(value = "Get User Reservations", notes = "User")
    public ResponseEntity<List<ReservationDto>> getUserReservation(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(reservationFacade.getUserReservation(username));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Cancel Reservation", notes = "User")
    public void cancelReservation(@RequestHeader("x-username") String username,
                                  @PathVariable("id") String reservationId) {

        reservationFacade.cancelByUser(username, reservationId);
    }

    @GetMapping("journey/{journeyId}")
    @ApiOperation(value = "Get Users for Journey", notes = "Guide")
    public ResponseEntity<List<BasicInfoUser>> getUsersForJourney(@PathVariable("journeyId") String journeyId) {

        List<BasicInfoUser> users = reservationFacade.getAllUserEmailsForJourney(journeyId)
                .stream()
                .map(e -> userFetcher.fetch(e))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}
