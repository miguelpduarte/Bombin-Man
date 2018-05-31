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
    private static final int MAX_EXPLODING_BONUS = 6;
    private static final int MAX_EXPLODING_PENALTY = -2;
    private static final int MAX_BOMBS_BONUS = 10;
    private static final int MAX_SPEED_BONUS = 6;
    private static final int MAX_SPEED_PENALTY = -2;
    private static final int MAX_BOMBS_PENALTY = 1;
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
        if (speedChanger < MAX_SPEED_BONUS) {
            speedChanger++;
        }
    }

    public void speedDown() {
        if (speedChanger > -MAX_SPEED_PENALTY) {
            speedChanger--;
        }
    }

    public void radiusUp() {
        if(explosionChanger < MAX_EXPLODING_BONUS){
            explosionChanger++;
        }
    }

    public void radiusDown() {
        if (explosionChanger > MAX_EXPLODING_PENALTY) {
            explosionChanger--;
        }
    }

    public void increaseAllowedBombs() {
        if(allowedBombsChanger < MAX_BOMBS_BONUS){
            allowedBombsChanger++;
        }
    }

    public void decreaseAllowedBombs() {
        if(allowedBombsChanger > MAX_BOMBS_PENALTY){
            allowedBombsChanger--;
        }
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
