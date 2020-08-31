package org.tanberg.excalc.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyExchange {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Map<String, Currency> currencies;

    public CurrencyExchange() {
        this.currencies = new ConcurrentHashMap<>();

        Currency currency = new Currency("NOK", "Norske kroner", new CurrencyRate(LocalDate.now(), 1.0));
        this.currencies.put(currency.getCode(), currency);

        InputStream stream = this.getClass().getResourceAsStream("rates.csv");
        this.importCSV(stream);
    }

    public Collection<Currency> getCurrencies() {
        return Collections.unmodifiableCollection(this.currencies.values());
    }

    public Currency getCurrency(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code can't be null or blank");
        }

        return this.currencies.get(code.toUpperCase());
    }

    public void importCSV(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.readLine(); // Ignore header

            String line;
            while ((line = reader.readLine()) != null) {
                this.importCSVCurrencyLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Internals

    private void importCSVCurrencyLine(String line) {
        // We pretty much just assume here that it isn't malformed
        String[] split = line.split(";");

        String code = split[0];
        String desc = split[1];
        CurrencyUnit unit = CurrencyUnit.fromIndex(Integer.parseInt(split[2]));
        LocalDate date = LocalDate.parse(split[3], FORMATTER);
        double value = Double.parseDouble(split[4]);

        CurrencyRate rate = new CurrencyRate(date, value / unit.getMultiplier());

        Currency currency = this.getCurrency(code);
        if (currency == null) {
            currency = new Currency(code.toUpperCase(), desc, rate);
            this.currencies.put(currency.getCode(), currency);
            return;
        }

        currency.addRate(rate);
    }
}
