package mage.fxclient.chat;

import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import mage.fxclient.server.ObservableServer;
import mage.fxclient.server.ServerEvent;
import mage.fxclient.server.ServerEventHandler;
import mage.remote.Session;
import mage.view.ChatMessage;

/**
 * FXML Controller class
 *
 * @author North
 */
public class ChatPanelController {

    @Inject
    Session session;

    @Inject
    ObservableServer observableServer;

    @FXML
    private ListView<MessageNode> messageList;
    @FXML
    private TextField messageField;

    final ObservableList<MessageNode> observableMessages = FXCollections.observableArrayList();

    private UUID chatId;

    @PostConstruct
    private void init() {
        String userName = session.getUserName();

        UUID mainRoomId = session.getMainRoomId();
        chatId = session.getRoomChatId(mainRoomId);
        session.joinChat(chatId);

        observableServer.on(ServerEvent.chatMessage, (ServerEventHandler<ChatMessage>) (objectId, message) -> {
            observableMessages.add(new MessageNode(message, userName));
        });
    }

    public void initialize() {
        messageList.setItems(observableMessages);
    }

    public void onAction() {
        String message = messageField.getText();
        messageField.setText("");

        session.sendChatMessage(chatId, message);
    }
}
