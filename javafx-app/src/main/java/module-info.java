module org.tanberg.excalc {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.controls;

    exports org.tanberg.excalc;

    opens org.tanberg.excalc to javafx.fxml;
}