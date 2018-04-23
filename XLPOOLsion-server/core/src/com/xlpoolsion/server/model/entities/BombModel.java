package com.xlpoolsion.server.model.entities;

import static com.xlpoolsion.server.view.GameView.PIXEL_TO_METER;

public class BombModel extends EntityModel {
    public static final float WIDTH = 48 * PIXEL_TO_METER;
    public static final float HEIGHT = 48 * PIXEL_TO_METER;
    public static final float EXPLOSION_DELAY = 1.0f;

    private float time_to_explosion;

    public BombModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public boolean decreaseTimeToExplosion(float delta) {
        time_to_explosion -= delta;
        return time_to_explosion < 0;
    }

    public void setTimeToExplosion(float time_to_explosion) {
        this.time_to_explosion = time_to_explosion;
    }
}
