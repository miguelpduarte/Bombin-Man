package com.xlpoolsion.common;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class ClientToServerMessage implements Serializable {
    public enum MessageType {
        CONTROLLER_SHAKE,
        PLAYER_MOVE,
        PRESSED_PLACE_BOMB,
        PRESSED_GRAB_BOMB,
        TEST_MESSAGE
    }

    public final MessageType messageType;
    public final Vector2 move_direction;

    public ClientToServerMessage(MessageType messageType, Vector2 move_direction) {
        this.messageType = messageType;
        this.move_direction = move_direction;
    }

    public ClientToServerMessage(MessageType messageType) {
        this(messageType, null);
    }
}
