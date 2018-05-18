package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.BreakableBrickView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public class BreakableBrickModel extends EntityModel {
    public static final float WIDTH = BreakableBrickView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = BreakableBrickView.HEIGHT * PIXEL_TO_METER;

    public BreakableBrickModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}
