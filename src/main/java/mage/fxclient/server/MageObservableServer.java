package mage.fxclient.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.utils.MageVersion;

/**
 *
 * @author North
 */
public class MageObservableServer implements MageClient, ObservableServer {

    private final MageVersion version = new MageVersion(1, 3, 0, "");

    private final Map<ServerEvent, List<ServerEventHandler>> handlers = new HashMap<>();

    private final List<SessionEventHandler> connectHandlers = new LinkedList<>();
    private final List<SessionEventHandler> disconnectHandlers = new LinkedList<>();
    private final List<SessionEventHandler> showMessageHandlers = new LinkedList<>();
    private final List<SessionEventHandler> showErrorHandlers = new LinkedList<>();

    @Override
    public MageVersion getVersion() {
        return version;
    }

    @Override
    public void connected(String message) {
        for (SessionEventHandler handler : connectHandlers) {
            handler.handle(message);
        }
    }

    @Override
    public void disconnected() {
        for (SessionEventHandler handler : disconnectHandlers) {
            handler.handle("");
        }
    }

    @Override
    public void showMessage(String message) {
        for (SessionEventHandler handler : showMessageHandlers) {
            handler.handle(message);
        }
    }

    @Override
    public void showError(String message) {
        for (SessionEventHandler handler : showErrorHandlers) {
            handler.handle(message);
        }
    }

    @Override
    public void processCallback(ClientCallback cc) {
        ServerEvent event = ServerEvent.fromMethod(cc.getMethod());
        List<ServerEventHandler> eventHandlers = handlers.get(event);
        if (eventHandlers != null) {
            for (ServerEventHandler handler : eventHandlers) {
                handler.handle(cc.getObjectId(), cc.getData());
            }
        }
    }

    @Override
    public void on(ServerEvent event, ServerEventHandler handler) {
        List<ServerEventHandler> eventHandlers = handlers.get(event);
        if (eventHandlers == null) {
            eventHandlers = new LinkedList<>();
        }

        eventHandlers.add(handler);

        if (eventHandlers.size() == 1) {
            handlers.put(event, eventHandlers);
        }
    }

    @Override
    public void onConnect(SessionEventHandler handler) {
        connectHandlers.add(handler);
    }

    @Override
    public void onDisconnect(SessionEventHandler handler) {
        disconnectHandlers.add(handler);
    }

    @Override
    public void onShowMessage(SessionEventHandler handler) {
        showMessageHandlers.add(handler);
    }

    @Override
    public void onShowError(SessionEventHandler handler) {
        showErrorHandlers.add(handler);
    }
}
