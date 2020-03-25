package com.journeyplanner.payment.infrastructure.input.rest;

import com.journeyplanner.payment.domain.account.AccountFacade;
import com.journeyplanner.payment.domain.account.TransferDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing/payments")
@Slf4j
@AllArgsConstructor
@Api(tags = "PaymentAPI")
public class PaymentController {

    private final AccountFacade accountFacade;

    @GetMapping("{id}")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get Payment", notes = "User")
    public ResponseEntity<TransferDto> getPayment(@RequestHeader("x-username") String username,
                                                  @PathVariable("id") String paymentId) {

        return ResponseEntity.ok(accountFacade.getTransactionStatus(username, paymentId));
    }

    @PostMapping("{id}/retry")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Retry Payment", notes = "User")
    public void retryPayment(@RequestHeader("x-username") String username,
                             @PathVariable("id") String paymentId) {

        accountFacade.retryTransaction(username, paymentId);
    }
}
