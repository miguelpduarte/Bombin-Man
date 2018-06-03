package com.xlpoolsion.common;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

/**
 * Represents a message from the Client to the Server. Serializable for ease of sending and receiving.
 */
public class ClientToServerMessage implements Serializable {
    /**
     * The type of the Message
     */
    public enum MessageType {
        CONTROLLER_SHAKE,
        PLAYER_MOVE,
        PRESSED_PLACE_BOMB,
        PRESSED_GRAB_BOMB,
        ACK
    }

    /**
     * The stored message type
     */
    public final MessageType messageType;
    /**
     * The stored movement direction
     */
    public final Vector2 move_direction;

    /**
     * Builds a message with the given type and movement direction
     * @param messageType Type of the message
     * @param move_direction Direction of movement
     */
    public ClientToServerMessage(MessageType messageType, Vector2 move_direction) {
        this.messageType = messageType;
        this.move_direction = move_direction;
    }

    /**
     * Builds a message without movement direction
     * @param messageType The type of the message
     */
    public ClientToServerMessage(MessageType messageType) {
        this(messageType, null);
    }
}
