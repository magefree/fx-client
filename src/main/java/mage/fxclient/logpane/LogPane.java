package mage.fxclient.logpane;

import java.util.LinkedList;
import java.util.Queue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author North
 */
public class LogPane extends Pane {

    private static final String htmlRelativePath = "/logpane/logPane.html";

    private final WebView webView;
    private final Queue<LogEntry> queue;

    private boolean loaded = false;

    public LogPane() {
        queue = new LinkedList<>();

        webView = new WebView();
        this.getChildren().add(webView);

        webView.setContextMenuEnabled(false);

        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(onLoadListener);

        String htmlPath = this.getClass().getResource(htmlRelativePath).toExternalForm();
        webEngine.load(htmlPath);
    }

    private final ChangeListener<State> onLoadListener = new ChangeListener<State>() {

        @Override
        public void changed(ObservableValue<? extends State> observable, State oldState, State newState) {
            if (newState == State.SUCCEEDED) {
                loaded = true;
                processQueue();
            }
        }
    };

    public void addEntry(LogEntry entry) {
        queue.add(entry);

        if (loaded) {
            processQueue();
        }
    }

    private void processQueue() {
        while (!queue.isEmpty()) {
            processLogEntry(queue.remove());
        }
    }

    private void processLogEntry(LogEntry entry) {
        webView.getEngine().executeScript("log.appendMessage(" + entry.toJSON() + ");");
    }
}
