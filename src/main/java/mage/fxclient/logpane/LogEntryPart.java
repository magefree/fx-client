package mage.fxclient.logpane;

import javafx.scene.paint.Color;

public class LogEntryPart {
    private final Color color;
    private final String text;

    public LogEntryPart(Color color, String text) {
        this.color = color;
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
