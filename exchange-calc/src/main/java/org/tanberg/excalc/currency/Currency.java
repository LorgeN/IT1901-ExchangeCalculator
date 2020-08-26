package org.tanberg.excalc.currency;

public enum Currency {
    NOK(1.0),
    USD(9.0),
    EUR(10.0),
    GBP(12.0);

    private final double exchangeRate;

    Currency(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public double exchange(double amount, Currency currency) {
        return amount * (this.getExchangeRate() / currency.getExchangeRate());
    }
}
