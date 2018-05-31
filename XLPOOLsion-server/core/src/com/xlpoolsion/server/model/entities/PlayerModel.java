package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public class PlayerModel extends EntityModel {
    public static final float WIDTH = 2.1f;
    public static final float HEIGHT = PlayerView.HEIGHT * PIXEL_TO_METER;

    private boolean moving = false;
    private boolean overBomb = false;
    private Orientation currentOrientation = Orientation.DOWN;
    private static final float STARTING_SPEED = 4.4f;
    private static final int STARTING_ALLOWED_BOMBS = 1;
    private static final int STARTING_EXPLOSION_RADIUS = 3;
    private int activeBombs = 0;
    private int id;

    //Powerups
    private int speedChanger = 0;
    private int explosionChanger = 0;
    private int allowedBombsChanger = 0;

    public PlayerModel(float x, float y, float rotation, int id) {
        super(x, y, rotation);
        this.id = id;
    }

    public float getCurrentSpeed() {
        return STARTING_SPEED + speedChanger;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public void setOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public int getExplosionRadius() {
        return STARTING_EXPLOSION_RADIUS + explosionChanger;
    }

    public void speedUp() {
        if (speedChanger < 6) {
            speedChanger++;
        }
    }

    public void speedDown() {
        if (speedChanger > -2) {
            speedChanger--;
        }
    }

    public void radiusUp() {
        if(explosionChanger < 6){
            explosionChanger++;
        }
    }

    public void radiusDown() {
        if (explosionChanger > -2) {
            explosionChanger--;
        }
    }

    public void increaseAllowedBombs() {
        allowedBombsChanger++;
    }

    public void decreaseAllowedBombs() {
        allowedBombsChanger--;
    }

    public boolean incrementActiveBombs() {
        if(activeBombs < STARTING_ALLOWED_BOMBS + allowedBombsChanger) {
            activeBombs++;
            return true;
        }
        return false;
    }

    public void decrementActiveBombs() {
        activeBombs--;
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
}
