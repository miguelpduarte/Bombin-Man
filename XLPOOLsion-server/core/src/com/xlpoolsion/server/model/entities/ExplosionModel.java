package com.xlpoolsion.server.model.entities;

public class ExplosionModel extends EntityModel {
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;
    public static final float EXPLOSION_DECAY_TIME = 0.5f;

    private float time_to_decay;

    public ExplosionModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public float getTimeToDecay() {
        return time_to_decay;
    }

    public void setTimeToDecay(float time_to_decay) {
        this.time_to_decay = time_to_decay;
    }

    public boolean decreaseTimeToDecay(float delta) {
        time_to_decay -= delta;
        return time_to_decay < 0;
    }
}
