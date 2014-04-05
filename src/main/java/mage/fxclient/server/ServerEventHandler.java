package mage.fxclient.server;

import java.util.UUID;

/**
 *
 * @author North
 * @param <T> data type
 */
public interface ServerEventHandler<T> {

    void handle(UUID objectId, T data);
}
