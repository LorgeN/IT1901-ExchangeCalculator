package org.tanberg.excalc;

import javafx.util.StringConverter;
import org.tanberg.excalc.currency.Currency;
import org.tanberg.excalc.currency.CurrencyExchange;

public class CurrencyStringConverter extends StringConverter<Currency> {

    private final CurrencyExchange exchange;


    public CurrencyStringConverter(CurrencyExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString(Currency currency) {
        if (currency == null) {
            return "-";
        }

        return currency.getCode();
    }

    @Override
    public Currency fromString(String s) {
        return this.exchange.getCurrency(s);
    }
}
