package mage.fxclient.controller;

import java.util.UUID;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.inject.Inject;
import mage.fxclient.logpane.LogEntry;
import mage.fxclient.logpane.LogPane;
import mage.fxclient.server.ObservableServer;
import mage.fxclient.server.ServerEvent;
import mage.fxclient.server.ServerEventHandler;
import mage.remote.Session;
import mage.view.ChatMessage;

public class ChatPanelController {

    @Inject
    private Session session;

    @Inject
    private ObservableServer observableServer;

    @FXML
    private LogPane logPane;
    @FXML
    private TextField messageField;

    private UUID chatId;

    public void initialize() {
        UUID mainRoomId = session.getMainRoomId();
        chatId = session.getRoomChatId(mainRoomId);
        session.joinChat(chatId);

        observableServer.on(ServerEvent.chatMessage, chatMessageHandler);
    }

    public void onAction() {
        String message = messageField.getText();
        messageField.setText("");

        session.sendChatMessage(chatId, message);
    }

    private final ServerEventHandler<ChatMessage> chatMessageHandler = (objectId, message) -> {
        Platform.runLater(() -> {
            logPane.addEntry(createLogEntry(message));
        });
    };

    private static final Color OPPONENT_COLOR = Color.rgb(238, 230, 133);
    private static final Color MY_COLOR = Color.rgb(0, 230, 64);
    private static final Color TIMESTAMP_COLOR = Color.rgb(255, 255, 0);
    private static final Color MESSAGE_COLOR = Color.WHITE;
    private static final Color USER_INFO_COLOR = Color.YELLOW;
    private static final Color STATUS_COLOR = Color.CYAN;

    private LogEntry createLogEntry(ChatMessage message) {
        LogEntry logEntry = new LogEntry();

        if (message.getTime() != null) {
            logEntry.addPart(TIMESTAMP_COLOR, message.getTime());
        }

        Color userColor;
        Color textColor;
        String messageUserName = message.getUsername();

        switch (message.getMessageType()) {
            case STATUS: // a message to all chat users
                textColor = STATUS_COLOR;
                userColor = STATUS_COLOR;
                break;
            case USER_INFO: // a personal message
                textColor = USER_INFO_COLOR;
                userColor = USER_INFO_COLOR;
                break;
            default:
                userColor = session.getUserName().equals(messageUserName) ? MY_COLOR : OPPONENT_COLOR;
                textColor = MESSAGE_COLOR;
        }
        switch (message.getColor()) {
            case ORANGE:
                textColor = Color.ORANGE;
                break;
            case YELLOW:
                textColor = Color.YELLOW;
                break;
        }
        if (messageUserName != null && !messageUserName.isEmpty()) {
            logEntry.addPart(userColor, messageUserName + ":");
        }

        logEntry.addPart(textColor, message.getMessage());

        return logEntry;
    }
}
