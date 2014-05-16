package mage.fxclient.logpane;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author North
 */
public class LogEntry {

    private final List<LogEntryPart> parts;

    public LogEntry() {
        parts = new LinkedList<>();
    }

    public void addPart(Color color, String text) {
        parts.add(new LogEntryPart(color, text));
    }

    public List<LogEntryPart> getParts() {
        return parts;
    }
}
