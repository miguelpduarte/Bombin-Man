package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.view.GameView.PIXEL_TO_METER;

public class PlayerModel extends EntityModel {
    public static final float WIDTH = PlayerView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = PlayerView.HEIGHT * PIXEL_TO_METER;

    //Temporary, to change to gets because it will be mutable via powerups
    public static final int EXPLOSION_RADIUS = 3;

    private boolean kickPowerup = true;
    private boolean moving = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private float current_speed = 4.4f;

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
