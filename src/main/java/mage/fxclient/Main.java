package mage.fxclient;

import java.util.UUID;
import mage.fxclient.server.MageObservableServer;
import mage.fxclient.server.ServerEvent;
import mage.fxclient.server.ServerEventHandler;
import mage.remote.Connection;
import mage.remote.SessionImpl;
import mage.view.ChatMessage;

/**
 *
 * @author North
 */
public class Main {

    public static void main(String[] args) {
        MageObservableServer serverObservable = new MageObservableServer();
        SessionImpl session = new SessionImpl(serverObservable);

        serverObservable.onConnect(message -> {
            System.out.println("connectMessage: " + message);
        });

        serverObservable.onDisconnect(message -> {
            System.out.println("disconnectMessage: " + message);
        });

        serverObservable.onShowMessage(message -> {
            System.out.println("showMessage: " + message);
        });

        serverObservable.onShowError(message -> {
            System.out.println("errorMessage: " + message);
        });

        serverObservable.on(ServerEvent.chatMessage, (ServerEventHandler<ChatMessage>) (objectId, message) -> {
            System.out.println(message.getUsername() + ": " + message.getMessage());
        });

        Connection connection = createConnectionObject();

        session.connect(connection);

        UUID mainRoomId = session.getMainRoomId();
        UUID chatId = session.getRoomChatId(mainRoomId);
        session.joinChat(chatId);
        session.sendChatMessage(chatId, "Greetings from the other world");

        session.disconnect(false);
    }

    private static Connection createConnectionObject() {
        Connection connection = new Connection();
        connection.setHost("localhost");
        connection.setPort(17171);
        connection.setUsername("TestUser");
        connection.setAvatarId(51);
        connection.setProxyType(Connection.ProxyType.NONE);
        connection.setShowAbilityPickerForced(true);
        return connection;
    }
}
