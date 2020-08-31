package org.tanberg.excalc.currency;

import java.time.LocalDate;
import java.util.Objects;

public class CurrencyRate {

    private final LocalDate date;
    private final double value;

    public CurrencyRate(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyRate that = (CurrencyRate) o;
        return Double.compare(that.getValue(), getValue()) == 0 &&
                getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getValue());
    }
}
