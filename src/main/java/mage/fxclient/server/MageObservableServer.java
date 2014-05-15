package mage.fxclient.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.utils.MageVersion;

/**
 *
 * @author North
 */
public class MageObservableServer implements MageClient, ObservableServer {

    private final MageVersion version = new MageVersion(1, 3, 0, "");

    private final Map<ServerEvent, Set<ServerEventHandler>> handlers = new HashMap<>();

    private final Set<SessionEventHandler> connectHandlers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<SessionEventHandler> disconnectHandlers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<SessionEventHandler> showMessageHandlers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<SessionEventHandler> showErrorHandlers = Collections.newSetFromMap(new WeakHashMap<>());

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
        Set<ServerEventHandler> eventHandlers = handlers.get(event);
        if (eventHandlers != null) {
            for (ServerEventHandler handler : eventHandlers) {
                handler.handle(cc.getObjectId(), cc.getData());
            }
        }
    }

    @Override
    public void on(ServerEvent event, ServerEventHandler handler) {
        Set<ServerEventHandler> eventHandlers = handlers.get(event);
        if (eventHandlers == null) {
            eventHandlers = Collections.newSetFromMap(new WeakHashMap<>());
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
