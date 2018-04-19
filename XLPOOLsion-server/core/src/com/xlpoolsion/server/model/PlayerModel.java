package com.xlpoolsion.server.model;

public class PlayerModel {
    private static final float PLAYER_SPEED = 3.7f;

    private int x = 0;
    private int y = 0;

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
