package com.xlpoolsion.server.model.entities;

import static com.xlpoolsion.server.view.GameView.PIXEL_TO_METER;

public class PlayerModel extends EntityModel {
    public static final float WIDTH = 16 * PIXEL_TO_METER;
    public static final float HEIGHT = 32 * PIXEL_TO_METER;

    private boolean moving = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 3.58f;

    public PlayerModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public float getCurrentSpeed() {
        return current_speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public enum Orientation {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }
}
