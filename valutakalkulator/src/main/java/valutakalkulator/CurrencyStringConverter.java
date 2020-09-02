package valutakalkulator;

import javafx.util.StringConverter;
import valutakalkulator.currency.Currency;
import valutakalkulator.currency.CurrencyExchange;

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
