package mage.fxclient.logpane;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LogPane extends ListView<TextFlow> {

    private final ObservableList<TextFlow> entries;

    public LogPane() {
        entries = FXCollections.observableArrayList();
        this.setItems(entries);
    }

    public void addEntry(LogEntry entry) {
        TextFlow entryNode = new TextFlow();
        entryNode.setStyle("-fx-background-color: Sienna;");

        List<LogEntryPart> parts = entry.getParts();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                entryNode.getChildren().add(new Text(" "));
            }
            entryNode.getChildren().add(createTextPart(parts.get(i)));
        }

        entries.add(entryNode);
        this.scrollTo(entryNode);
    }

    private Text createTextPart(LogEntryPart logEntryPart) {
        Text text = new Text(logEntryPart.getText());
        text.setFill(logEntryPart.getColor());

        return text;
    }
}
