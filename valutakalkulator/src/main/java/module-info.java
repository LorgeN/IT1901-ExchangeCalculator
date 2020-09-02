module org.tanberg.excalc {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.controls;

    exports valutakalkulator;
    exports valutakalkulator.currency;

    opens valutakalkulator.currency;
    opens valutakalkulator to javafx.fxml;
}