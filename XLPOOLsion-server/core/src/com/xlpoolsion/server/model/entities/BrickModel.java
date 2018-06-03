package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.BrickView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single Brick
 */
public class BrickModel extends EntityModel {
    public static final float WIDTH = BrickView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = BrickView.HEIGHT * PIXEL_TO_METER;

    /**
     * Creates a new Brick model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public BrickModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}
