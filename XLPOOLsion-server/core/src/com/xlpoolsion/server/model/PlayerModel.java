package com.xlpoolsion.server.model;

public class PlayerModel {

    private float x = 0;
    private float y = 0;
    private boolean moving = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 2.2f;

    public float getCurrentSpeed() {
        return current_speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
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
