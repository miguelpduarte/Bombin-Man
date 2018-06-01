package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.StunPowerView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public class StunPowerModel extends EntityModel {
    public StunPowerModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public static final float WIDTH = StunPowerView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = StunPowerView.HEIGHT * PIXEL_TO_METER;
}
