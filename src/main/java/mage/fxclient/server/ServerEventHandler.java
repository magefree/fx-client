package mage.fxclient.server;

import java.util.UUID;

public interface ServerEventHandler {

    void handle(UUID objectId, Object data);
}
