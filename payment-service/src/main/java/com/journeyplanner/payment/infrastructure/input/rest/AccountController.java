package com.journeyplanner.payment.infrastructure.input.rest;

import com.journeyplanner.payment.domain.account.AccountDto;
import com.journeyplanner.payment.domain.account.AccountFacade;
import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import com.journeyplanner.payment.infrastructure.input.response.AccountChargedResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/billing/accounts")
@Slf4j
@AllArgsConstructor
@Api(tags = "AccountAPI")
public class AccountController {

    private final AccountFacade accountFacade;

    @GetMapping
    @ApiOperation(value = "Get User Account", notes = "User")
    public ResponseEntity<AccountDto> get(@RequestHeader("x-username") String username) {

        return ResponseEntity.ok(accountFacade.getAccountByEmail(username));
    }

    @PostMapping("charge")
    @ApiOperation(value = "Charge Account", notes = "User")
    public ResponseEntity<AccountChargedResponse> charge(@RequestHeader("x-username") String username,
                                         @RequestBody @Valid ChargeAccountRequest request) {

        return ResponseEntity.ok(new AccountChargedResponse(accountFacade.chargeAccount(username, request)));
    }
}
