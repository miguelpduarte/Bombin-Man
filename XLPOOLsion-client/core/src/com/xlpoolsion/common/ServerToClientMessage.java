package com.xlpoolsion.common;

import java.io.Serializable;

public class ServerToClientMessage implements Serializable {
    public enum MessageType {
        START_GAME,
        SERVER_FULL,
        YOU_ARE_STUNNED,
        YOU_ARE_NO_LONGER_STUNNED,
        YOU_WON,
        YOU_LOST
    }

    public final MessageType messageType;

    public ServerToClientMessage(MessageType messageType) {
        this.messageType = messageType;
    }
}
