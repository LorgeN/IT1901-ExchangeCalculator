package org.tanberg.excalc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AppController {
    
    @FXML
    public Button okButton;

    @FXML
    public Text displayText;

    @FXML
    public TextField inputField;

    @FXML
    void handleOkAction() {
        this.displayText.setText(this.inputField.getText());
    }
}
