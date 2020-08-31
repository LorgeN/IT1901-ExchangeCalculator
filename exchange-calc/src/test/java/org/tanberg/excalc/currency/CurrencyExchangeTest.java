package org.tanberg.excalc.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyExchangeTest {

    @Test
    public void testExchange() {
        CurrencyExchange exchange = new CurrencyExchange();

        Assertions.assertNotNull(exchange.getCurrency("USD"));
        Assertions.assertNotNull(exchange.getCurrency("NOK"));
        Assertions.assertNotNull(exchange.getCurrency("GBP"));
    }
}