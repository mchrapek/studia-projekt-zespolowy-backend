package com.journeyplanner.payment.infrastructure.input.rest;

import com.journeyplanner.payment.domain.account.AccountDto;
import com.journeyplanner.payment.domain.account.AccountFacade;
import com.journeyplanner.payment.domain.account.TransferDto;
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class PaymentController {

    private final AccountFacade accountFacade;

    @GetMapping("account")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AccountDto> get(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(accountFacade.getAccountByEmail(username));
    }

    @GetMapping("payment/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TransferDto> getPayment(@RequestHeader("x-username") String username,
                                                  @PathVariable String id) {

        return ResponseEntity.ok(accountFacade.getTransactionStatus(username, id));
    }

    @PostMapping("payment/{id}")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.OK)
    public void retryPayment(@RequestHeader("x-username") String username,
                             @PathVariable String id) {

        accountFacade.retryTransaction(username, id);
    }

    @PostMapping("charge")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> charge(@RequestHeader("x-username") String username,
                                         @RequestBody @Valid ChargeAccountRequest request) {

        return ResponseEntity.ok(accountFacade.chargeAccount(username, request));
    }
}
