package mage.fxclient.logpane;

import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author North
 */
public class LogEntry {

    private static final Gson gson = new Gson();

    private final List<LogEntryPart> parts;

    public LogEntry() {
        parts = new LinkedList<>();
    }

    public void addPart(Color color, String text) {
        parts.add(new LogEntryPart(color, text));
    }

    public String toJSON() {
        return gson.toJson(parts);
    }

    private static class LogEntryPart {

        private final String color;
        private final String text;

        public LogEntryPart(Color color, String text) {
            super();
            this.color = colorToRGB(color);
            this.text = text;
        }

        public String getColor() {
            return color;
        }

        public String getText() {
            return text;
        }

        private String colorToRGB(Color color) {
            return String.format("#%02X%02X%02X",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
        }
    }
}
