package org.tanberg.excalc;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.tanberg.excalc.currency.Currency;
import org.tanberg.excalc.currency.CurrencyExchange;
import org.tanberg.excalc.currency.CurrencyRate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class AppController {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
    private static final String DEFAULT_RESULT = "= ?";
    private static final LocalDate STAT_CUT_OFF = LocalDate.of(2020, 1, 1);

    @FXML
    public LineChart<String, Number> rateChart;

    @FXML
    private Text resultText;

    @FXML
    private TextField inputField;

    @FXML
    private ComboBox<Currency> fromComboBox;

    @FXML
    private ComboBox<Currency> toComboBox;

    private CurrencyExchange exchange;
    private Currency graphFrom;
    private Currency graphTo;

    @FXML
    public void initialize() {
        this.exchange = new CurrencyExchange();

        this.rateChart.getXAxis().setLabel("Dato");
        this.rateChart.getYAxis().setLabel("Verdi");

        this.rateChart.setTitle("-");
        this.rateChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Kurs");

        this.rateChart.getData().add(series);

        this.fromComboBox.setConverter(new CurrencyStringConverter(this.exchange));
        this.toComboBox.setConverter(new CurrencyStringConverter(this.exchange));

        ObservableList<Currency> fromItems = this.fromComboBox.getItems();
        ObservableList<Currency> toItems = this.toComboBox.getItems();

        for (Currency currency : this.exchange.getCurrencies()) {
            fromItems.add(currency);
            toItems.add(currency);
        }

        fromItems.sort(Comparator.comparing(Currency::getCode));
        toItems.sort(Comparator.comparing(Currency::getCode));

        this.resultText.setText(DEFAULT_RESULT);

        this.inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isBlank()) {
                    Double.parseDouble(newValue);
                }
            } catch (NumberFormatException e) {
                this.inputField.setText(oldValue);
                return;
            }

            this.updateConversion(newValue.isBlank() ? -1.0 : Double.parseDouble(newValue), this.getFromCurrency(), this.getToCurrency());
        });

        this.fromComboBox.valueProperty().addListener((observableValue, old, newValue) -> {
            if (Objects.equals(old, newValue)) {
                return;
            }

            this.updateConversion(this.getAmount(), newValue, this.getToCurrency());
        });

        this.toComboBox.valueProperty().addListener((value, old, newValue) -> {
            if (Objects.equals(old, newValue)) {
                return;
            }

            this.updateConversion(this.getAmount(), this.getFromCurrency(), newValue);
        });
    }

    public double getAmount() {
        String text = this.inputField.getText();
        if (text == null || text.isBlank()) {
            return -1.0;
        }

        return Double.parseDouble(text);
    }

    public Currency getFromCurrency() {
        return this.fromComboBox.getValue();
    }

    public Currency getToCurrency() {
        return this.toComboBox.getValue();
    }

    public void updateConversion(double amount, Currency fromCurrency, Currency toCurrency) {
        this.updateGraph(fromCurrency, toCurrency);
        if (fromCurrency == null || toCurrency == null) {
            this.resultText.setText(DEFAULT_RESULT);
            return;
        }

        if (amount == -1.0) {
            this.resultText.setText(DEFAULT_RESULT);
            return;
        }

        double converted = fromCurrency.exchangeTo(amount, toCurrency);
        this.resultText.setText("= " + FORMAT.format(converted) + " " + toCurrency.getCode());
    }

    public void updateGraph(Currency from, Currency to) {
        if (from == null || to == null) {
            this.rateChart.setTitle("-");
            this.rateChart.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Kurs");

            this.rateChart.getData().add(series);

            this.graphFrom = null;
            this.graphTo = null;
            return;
        }

        if (Objects.equals(this.graphFrom, from) && Objects.equals(this.graphTo, to)) {
            return;
        }

        this.graphFrom = from;
        this.graphTo = to;

        this.rateChart.setTitle(from.getDescription() + " til " + to.getDescription());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Kurs");

        CurrencyRate[] rates = from.getRates();

        double lowVal = 0;
        double highVal = 0;

        // Subtract 7 for weekly (ish)
        for (int i = rates.length - 1; i >= 0; i -= 7) {
            CurrencyRate rate = rates[i];
            LocalDate date = rate.getDate();
            if (date.isBefore(STAT_CUT_OFF)) {
                continue;
            }

            double value = from.exchangeTo(1.0, date, to);
            lowVal = lowVal == 0 ? value : Math.min(value, lowVal);
            highVal = highVal == 0 ? value : Math.max(value, highVal);

            series.getData().add(new XYChart.Data<>(date.toString(), value));
        }


        NumberAxis yAxis = (NumberAxis) this.rateChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(Math.ceil(highVal));
        yAxis.setLowerBound(Math.floor(lowVal));
        yAxis.setTickUnit(0.5);

        this.rateChart.getData().clear();
        this.rateChart.getData().add(series);
    }
}
