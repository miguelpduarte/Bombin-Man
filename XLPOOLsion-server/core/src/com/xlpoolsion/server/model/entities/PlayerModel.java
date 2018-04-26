package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.view.GameView.PIXEL_TO_METER;

public class PlayerModel extends EntityModel {
    public static final float WIDTH = PlayerView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = PlayerView.HEIGHT * PIXEL_TO_METER;

    private boolean moving = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 4.4f;

    //Powerups
    private boolean kickPowerup = true;
    private int explosion_radius = 3;

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

    public int getExplosionRadius() {
        return explosion_radius;
    }

    //TODO: Change to increment maybe so that powerups are used directly without get?
    public void setExplosionRadius(int explosion_radius) {
        this.explosion_radius = explosion_radius;
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

    public boolean hasKickPowerup() {
        return kickPowerup;
    }

    public void setKickPowerup(boolean kickPowerup) {
        this.kickPowerup = kickPowerup;
    }
}
