package com.journeyplanner.payment.domain.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class AccountDto {

    String id;
    BigDecimal balance;
    List<AccountHistoryEventDto> history;

    static AccountDto from(final Account account, final List<AccountHistory> accountHistoryEvents) {
        return AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .history(accountHistoryEvents
                        .stream()
                        .map(e -> AccountHistoryEventDto.builder()
                                .id(e.getId())
                                .type(e.getType())
                                .createdTime(e.getCreatedTime())
                                .value(e.getValue())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }

}

@Value
@Builder
class AccountHistoryEventDto {

    String id;
    Instant createdTime;
    AccountEventType type;
    BigDecimal value;
}
