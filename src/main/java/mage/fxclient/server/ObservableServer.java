package mage.fxclient.server;

/**
 *
 * @author North
 */
public interface ObservableServer {

    @SuppressWarnings("rawtypes")
    void on(ServerEvent event, ServerEventHandler handler);

    void onConnect(SessionEventHandler handler);

    void onDisconnect(SessionEventHandler handler);

    void onShowMessage(SessionEventHandler handler);

    void onShowError(SessionEventHandler handler);
}
