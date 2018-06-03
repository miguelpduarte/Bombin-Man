package com.xlpoolsion.common;

import java.io.Serializable;

/**
 * Represents a message from the Server to the Client. Serializable for ease of sending and receiving.
 */
public class ServerToClientMessage implements Serializable {
    /**
     * The type of the Message
     */
    public enum MessageType {
        START_GAME,
        SERVER_FULL,
        YOU_ARE_STUNNED,
        YOU_ARE_NO_LONGER_STUNNED,
        YOU_WON,
        YOU_LOST
    }

    /**
     * The stored message type
     */
    public final MessageType messageType;

    /**
     * Builds a message with the given type
     * @param messageType Type of the message
     */
    public ServerToClientMessage(MessageType messageType) {
        this.messageType = messageType;
    }
}
