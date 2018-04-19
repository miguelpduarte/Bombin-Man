package com.xlpoolsion.server.model;

public class PlayerModel {
    private static final float PLAYER_SPEED = 2.5f;

    private int x = 0;
    private int y = 0;
    private boolean moving = false;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public enum Orientation {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public void moveUp() {
        this.y += PLAYER_SPEED;
        this.moving = true;
        this.currentOrientation = Orientation.UP;
    }

    public void moveRight() {
        this.x += PLAYER_SPEED;
        this.moving = true;
        this.currentOrientation = Orientation.RIGHT;
    }

    public void moveLeft() {
        this.x -= PLAYER_SPEED;
        this.moving = true;
        this.currentOrientation = Orientation.LEFT;
    }

    public void moveDown() {
        this.y -= PLAYER_SPEED;
        this.moving = true;
        this.currentOrientation = Orientation.DOWN;
    }

    public void stop() {
        this.moving = false;
    }

    private Orientation currentOrientation = Orientation.DOWN;

    public PlayerModel() {

    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }
}
