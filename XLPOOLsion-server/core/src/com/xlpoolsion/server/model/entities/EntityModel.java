package com.xlpoolsion.server.model.entities;

public class EntityModel {
    private float x = 0;
    private float y = 0;

    private float rotation = 0;
    private boolean flaggedForRemoval = false;

    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

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

    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }
}
