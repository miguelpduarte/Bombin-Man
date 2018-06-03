package com.xlpoolsion.server.model.entities;


import com.xlpoolsion.server.view.entities.SpeedDownView;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single PowerDown with a certain type
 */
public  class PowerDownModel extends EntityModel {
    /**
     * The types of PowerDowns
     */
    public enum PowerDownType {
        SpeedDown,
        BombRadDown,
        BombsDown,
        RandomDown,
    }

    public static final float WIDTH = SpeedDownView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = SpeedDownView.HEIGHT * PIXEL_TO_METER;

    private PowerDownType type;

    /**
     * Creates a new PowerDown model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public PowerDownModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.type = PowerDownType.values()[random.nextInt(PowerDownType.values().length)];
    }

    /**
     * Gets the type of PowerDown
     * @return type of PowerDown
     */
    public PowerDownType getType(){
        return type;
    }

}
