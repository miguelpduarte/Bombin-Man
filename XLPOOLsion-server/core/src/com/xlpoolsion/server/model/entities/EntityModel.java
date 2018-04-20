package com.xlpoolsion.server.model.entities;

public class EntityModel {
    private float x = 0;
    private float y = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
