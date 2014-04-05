package mage.fxclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class FXMLController implements Initializable {

    @Inject
    String message;

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
