package com.journeyplanner.payment.domain.account;

import java.math.BigDecimal;

interface AccountCustomRepository {

    void modifyAccountBalance(String id, BigDecimal balance);
}
