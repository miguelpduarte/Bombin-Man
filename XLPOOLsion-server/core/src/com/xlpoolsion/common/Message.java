package com.xlpoolsion.common;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Message implements Serializable {
    public enum MessageType {
        CONTROLLER_SHAKE,
        PLAYER_MOVE,
        TEST_MESSAGE
    }

    public final MessageType messageType;
    public final Vector2 move_direction;

    public Message(MessageType messageType, Vector2 move_direction) {
        this.messageType = messageType;
        this.move_direction = move_direction;
    }

    public Message(MessageType messageType) {
        this(messageType, null);
    }
}
