package mage.fxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mage.fxclient.injection.InjectionProvider;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final InjectionProvider injectionProvider = new InjectionProvider();
        injectionProvider.addDependency(String.class, "Hello dependency injection.");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        loader.setControllerFactory((Class<?> clazz) -> {
            return injectionProvider.createInstance(clazz);
        });

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
