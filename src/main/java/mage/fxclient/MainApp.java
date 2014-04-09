package mage.fxclient;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mage.fxclient.injection.InjectionProvider;
import mage.fxclient.server.MageObservableServer;
import mage.fxclient.server.ObservableServer;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;

public class MainApp extends Application {

    private final InjectionProvider injectionProvider;

    public MainApp() {
        MageObservableServer observableServer = new MageObservableServer();
        Session session = new SessionImpl(observableServer);

        injectionProvider = new InjectionProvider();
        injectionProvider.addDependency(Session.class, session);
        injectionProvider.addDependency(ObservableServer.class, observableServer);

        session.connect(createConnectionObject());
    }

    private static Connection createConnectionObject() {
        Connection connection = new Connection();
        connection.setHost("localhost");
        connection.setPort(17171);
        connection.setUsername("TestUser");
        connection.setAvatarId(51);
        connection.setProxyType(Connection.ProxyType.NONE);
        connection.setShowAbilityPickerForced(true);
        return connection;
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlPath = "/fxml/ChatPanel.fxml";

        stage.setTitle("JavaFX and Maven");
        stage.setScene(new Scene(loadFxml(fxmlPath)));
        stage.show();
    }

    private Parent loadFxml(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setControllerFactory((Class<?> clazz) -> {
            return injectionProvider.createInstance(clazz);
        });

        return loader.load();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
