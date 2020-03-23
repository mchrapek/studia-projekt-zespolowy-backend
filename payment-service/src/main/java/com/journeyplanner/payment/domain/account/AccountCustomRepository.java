package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;
import java.time.Instant;

interface AccountCustomRepository {

    void modifyAccountBalance(String id, BigDecimal balance);

    void modifyAccountBalanceAndEventTime(String id, Instant eventTime, BigDecimal balance);
}
