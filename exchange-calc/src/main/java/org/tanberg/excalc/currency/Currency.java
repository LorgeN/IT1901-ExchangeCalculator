package org.tanberg.excalc.currency;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Currency {

    private final String code;
    private final String description;
    private CurrencyRate[] rates;

    public Currency(String code, String desc, CurrencyRate... rates) {
        this.code = code;
        this.description = desc;
        this.rates = rates;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public CurrencyRate[] getRates() {
        return rates;
    }

    public void addRate(CurrencyRate rate) {
        LocalDate date = rate.getDate();

        // Find first date that is before the currently given date
        int insertionIndex;
        for (insertionIndex = 0; insertionIndex < this.rates.length; insertionIndex++) {
            if (date.isAfter(this.rates[insertionIndex].getDate())) {
                break;
            }
        }

        this.rates = Arrays.copyOf(this.rates, this.rates.length + 1);
        System.arraycopy(this.rates, insertionIndex, this.rates, insertionIndex + 1, this.rates.length - insertionIndex - 1);
        this.rates[insertionIndex] = rate;
    }

    public double getExchangeRate(LocalDate date) {
        return Arrays.stream(this.rates)
                .filter(rate -> rate.getDate().isEqual(date))
                .mapToDouble(CurrencyRate::getValue)
                .findAny().orElse(this.getExchangeRate());
    }

    public double getExchangeRate() {
        return this.rates[0].getValue();
    }

    public double exchangeTo(double amount, Currency other) {
        return amount * (this.getExchangeRate() / other.getExchangeRate());
    }

    public double exchangeTo(double amount, LocalDate date, Currency other) {
        return amount * (this.getExchangeRate(date) / other.getExchangeRate(date));
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
                getCode().equals(currency.getCode()) &&
                getDescription().equals(currency.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDescription(), getExchangeRate());
    }
}
