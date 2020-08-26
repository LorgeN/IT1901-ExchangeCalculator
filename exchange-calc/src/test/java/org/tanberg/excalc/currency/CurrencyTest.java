package org.tanberg.excalc.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyTest {

    @Test
    void exchangeTo() {
        Assertions.assertEquals(9.0, Currency.USD.exchange(1.0, Currency.NOK));
        Assertions.assertEquals(1.0, Currency.NOK.exchange(9.0, Currency.USD));

        Assertions.assertEquals(9.0, Currency.USD.exchange(10.0, Currency.EUR));
        Assertions.assertEquals(10.0, Currency.EUR.exchange(9.0, Currency.USD));
    }
}