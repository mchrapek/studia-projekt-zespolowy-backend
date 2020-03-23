package com.journeyplanner.payment.domain.account;

import com.journeyplanner.payment.infrastructure.input.request.ChargeAccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class AccountFacade {

    private final AccountRepository repository;
    private final AccountCreator creator;

    public AccountDto getAccountByEmail(String email) {
        return repository.findByEmail(email)
                .map(AccountDto::from)
                .orElseGet(() -> AccountDto.from(repository.save(creator.emptyAccount(email))));
    }

    public String chargeAccount(String email, ChargeAccountRequest request) {
        Account account = repository.findByEmail(email)
                .orElseGet(() -> repository.save(creator.emptyAccount(email)));

        repository.modifyAccountBalance(account.getId(), account.getBalance().add(request.getValue()));
        String eventId = UUID.randomUUID().toString();
        account.getEvents().add(AccountHistoryEvent.builder()
                .id(eventId)
                .createdTime(Instant.now())
                .type(PaymentType.CHARGE)
                .value(request.getValue())
                .build());


        return eventId;
    }
}
