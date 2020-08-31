package org.tanberg.excalc.currency;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Currency {

    private final String key;
    private final String description;
    private CurrencyRate[] rates;

    public Currency(String key, String desc, CurrencyRate... rates) {
        this.key = key;
        this.description = desc;
        this.rates = rates;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public CurrencyRate[] getRates() {
        return rates;
    }

    public void addRate(CurrencyRate rate) {
        LocalDate date = rate.getDate();

        // Find last date that is before the currently given date
        int insertionIndex;
        for (insertionIndex = 0; insertionIndex < this.rates.length; insertionIndex++) {
            if (date.isAfter(this.rates[insertionIndex].getDate())) {
                continue;
            }

            break;
        }

        this.rates = Arrays.copyOf(this.rates, this.rates.length + 1);
        System.arraycopy(this.rates, insertionIndex - 1, this.rates, insertionIndex, this.rates.length - insertionIndex);
        this.rates[insertionIndex] = rate;
    }

    public double getExchangeRate() {
        return this.rates[0].getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Currency currency = (Currency) o;
        return Double.compare(currency.getExchangeRate(), getExchangeRate()) == 0 &&
                getKey().equals(currency.getKey()) &&
                getDescription().equals(currency.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getDescription(), getExchangeRate());
    }
}
