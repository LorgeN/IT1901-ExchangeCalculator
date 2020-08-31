package org.tanberg.excalc.currency;

public enum CurrencyUnit {
    SINGLE(1),
    UNKNOWN(0),
    HUNDRED(100);

    private final int multiplier;

    CurrencyUnit(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public static CurrencyUnit fromIndex(int index) {
        return values()[index];
    }
}
