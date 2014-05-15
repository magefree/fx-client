package mage.fxclient;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mage.fxclient.injection.InjectionProvider;

public class ViewFactory {

    private final InjectionProvider injectionProvider;

    public ViewFactory(InjectionProvider injectionProvider) {
        this.injectionProvider = injectionProvider;
    }

    public Parent create(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory((Class<?> clazz) -> {
            return injectionProvider.createInstance(clazz);
        });

        return loader.load();
    }
}
