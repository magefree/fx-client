package mage.fxclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import mage.remote.Connection;
import mage.remote.Session;

public class ConnectDialogController {

    @Inject
    private Session session;

    @FXML
    private TextField serverInput;
    @FXML
    private TextField portInput;
    @FXML
    private TextField userNameInput;

    public void onConnect() {
        session.connect(createConnectionObject());
    }

    private Connection createConnectionObject() {
        Connection connection = new Connection();
        connection.setHost(serverInput.getText());
        connection.setPort(Integer.parseInt(portInput.getText()));
        connection.setUsername(userNameInput.getText());
        connection.setAvatarId(51);
        connection.setProxyType(Connection.ProxyType.NONE);
        connection.setShowAbilityPickerForced(true);
        return connection;
    }
}
