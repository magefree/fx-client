package mage.fxclient;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mage.fxclient.injection.InjectionProvider;
import mage.fxclient.server.MageObservableServer;
import mage.fxclient.server.ObservableServer;
import mage.remote.Session;
import mage.remote.SessionImpl;

public class MainApp extends Application {

    private final InjectionProvider injectionProvider;

    private final ViewFactory viewFactory;

    public MainApp() {
        MageObservableServer observableServer = new MageObservableServer();
        Session session = new SessionImpl(observableServer);

        injectionProvider = new InjectionProvider();

        viewFactory = new ViewFactory(injectionProvider);
        injectionProvider.addDependency(ViewFactory.class, viewFactory);

        injectionProvider.addDependency(Session.class, session);
        injectionProvider.addDependency(ObservableServer.class, observableServer);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("MAGE");
        stage.setScene(new Scene(viewFactory.create("/fxml/main.fxml")));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
