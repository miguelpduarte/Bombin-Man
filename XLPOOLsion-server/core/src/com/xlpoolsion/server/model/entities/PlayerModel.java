package com.xlpoolsion.server.model.entities;

public class PlayerModel extends EntityModel {
    private boolean moving = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 3.58f;

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

    public PlayerModel() {

    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }
}
