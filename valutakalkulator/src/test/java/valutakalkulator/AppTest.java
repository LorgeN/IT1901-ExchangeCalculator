package valutakalkulator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

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

    }
}
