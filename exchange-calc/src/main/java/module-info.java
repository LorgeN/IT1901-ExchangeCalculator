module org.tanberg.excalc {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.controls;

    exports org.tanberg.excalc;
    exports org.tanberg.excalc.currency;

    opens org.tanberg.excalc.currency;
    opens org.tanberg.excalc to javafx.fxml;
}