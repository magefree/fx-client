package mage.fxclient.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import mage.fxclient.View;
import mage.fxclient.ViewFactory;
import mage.fxclient.server.ObservableServer;
import mage.fxclient.server.SessionEventHandler;
import mage.remote.Session;

public class MainController {

    @Inject
    private ViewFactory viewFactory;
    @Inject
    private Session session;
    @Inject
    private ObservableServer observableServer;

    @FXML
    private AnchorPane viewContainer;
    @FXML
    private Button connectButton;
    @FXML
    private Button lobbyButton;

    public void initialize() {
        connectButton.setOnAction(onConnectAction);

        observableServer.onConnect(onServerConnect);
        observableServer.onDisconnect(onServerDisconnect);

        lobbyButton.setDisable(true);
        lobbyButton.setOnAction(onLobbyAction);
    }

    private final EventHandler<ActionEvent> onConnectAction = event -> {
        changeView(View.connectDialog);
    };

    private final EventHandler<ActionEvent> onLobbyAction = event -> {
        changeView(View.chatPanel);
    };

    private final SessionEventHandler onServerConnect = message -> {
        connectButton.setText("Disconnect");
        connectButton.setOnAction(event -> {
            session.disconnect(false);
        });

        lobbyButton.setDisable(false);
    };

    private final SessionEventHandler onServerDisconnect = message -> {
        connectButton.setText("Connect");
        connectButton.setOnAction(onConnectAction);

        lobbyButton.setDisable(true);
    };

    private void changeView(View view) {
        try {
            Parent viewNode = viewFactory.create(view.getFxmlPath());
            viewContainer.getChildren().clear();
            viewContainer.getChildren().add(viewNode);

            AnchorPane.setBottomAnchor(viewNode, 0d);
            AnchorPane.setTopAnchor(viewNode, 0d);
            AnchorPane.setLeftAnchor(viewNode, 0d);
            AnchorPane.setRightAnchor(viewNode, 0d);
        } catch (IOException ex) {
        }
    }
}
