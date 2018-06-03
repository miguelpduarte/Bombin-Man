package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.BreakableBrickView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 *  A model representing a single BreakableBrick
 */
public class BreakableBrickModel extends EntityModel {
    public static final float WIDTH = BreakableBrickView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = BreakableBrickView.HEIGHT * PIXEL_TO_METER;

    /**
     * Creates a new Breakable Brick model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public BreakableBrickModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}
