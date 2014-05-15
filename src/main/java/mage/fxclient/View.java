package mage.fxclient;

public enum View {

    chatPanel("/fxml/ChatPanel.fxml"),
    connectDialog("/fxml/connectDialog.fxml");

    private final String fxmlPath;

    private View(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
