package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.StunPowerView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a stunPower
 */
public class StunPowerModel extends EntityModel {
    /**
     * Creates a new StunPower model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public StunPowerModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public static final float WIDTH = StunPowerView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = StunPowerView.HEIGHT * PIXEL_TO_METER;
}
