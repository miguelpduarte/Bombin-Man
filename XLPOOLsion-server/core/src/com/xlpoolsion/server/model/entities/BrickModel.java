package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.BrickView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public class BrickModel extends EntityModel {
    public static final float WIDTH = BrickView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = BrickView.HEIGHT * PIXEL_TO_METER;

    public BrickModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }


}
