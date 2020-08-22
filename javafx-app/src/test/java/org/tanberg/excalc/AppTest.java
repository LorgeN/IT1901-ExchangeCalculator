package org.tanberg.excalc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AppTest extends ApplicationTest {

    private Parent parent;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));

        this.parent = fxmlLoader.load();

        stage.setScene(new Scene(this.parent));
        stage.show();
    }

    @Test
    public void testController() {
        Button clickMeButton = (Button) this.parent.lookup("#clickMeButton");

        String oldText = clickMeButton.getText();

        this.clickOn(clickMeButton);

        Assertions.assertNotEquals(oldText, clickMeButton.getText());
    }
}
