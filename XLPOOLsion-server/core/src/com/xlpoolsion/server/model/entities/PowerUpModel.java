package com.xlpoolsion.server.model.entities;


import com.xlpoolsion.server.view.entities.SpeedUpView;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single PowerUp with a certain type
 */
public  class PowerUpModel extends EntityModel {
    /**
     * Different types of PowerUps
     */
    public enum PowerUpType {
        SpeedUp,
        BombRadUp,
        BombsUp,
        RandomUp,
    }

    public static final float WIDTH = SpeedUpView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = SpeedUpView.HEIGHT * PIXEL_TO_METER;

    private PowerUpType type;

    /**
     * Creates a new PowerUp model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public PowerUpModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.type = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
    }

    /**
     * Gets the type of PowerUp
     * @return type of PowerUp
     */
    public PowerUpType getType(){
        return type;
    }
}
