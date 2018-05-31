package com.xlpoolsion.server.model.entities;


import com.xlpoolsion.server.view.entities.PowerUpView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public  class PowerUpModel extends EntityModel {
    public static final float WIDTH = PowerUpView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = PowerUpView.HEIGHT * PIXEL_TO_METER;

    public PowerUpModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

}
