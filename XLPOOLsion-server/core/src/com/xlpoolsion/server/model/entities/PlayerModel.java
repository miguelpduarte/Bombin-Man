package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public class PlayerModel extends EntityModel {
    public static final float WIDTH = 2.1f;
    public static final float HEIGHT = PlayerView.HEIGHT * PIXEL_TO_METER;

    private boolean moving = false;
    private boolean overBomb = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 4.4f;
    private int id;

    //Powerups
    private int explosion_radius = 3;

    public PlayerModel(float x, float y, float rotation, int id) {
        super(x, y, rotation);
        this.id = id;
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

    public int getExplosionRadius() {
        return explosion_radius;
    }

    //TODO: Change to increment maybe so that powerups are used directly without get?
    public void setExplosionRadius(int explosion_radius) {
        this.explosion_radius = explosion_radius;
    }

    public boolean isOverBomb() {
        return overBomb;
    }

    public void setOverBomb(boolean overBomb) {
        this.overBomb = overBomb;
    }

    public int getId() {
        return id;
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
