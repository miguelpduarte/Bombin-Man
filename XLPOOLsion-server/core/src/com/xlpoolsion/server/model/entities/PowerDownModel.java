package com.xlpoolsion.server.model.entities;


import com.xlpoolsion.server.view.entities.SpeedDownView;
import com.xlpoolsion.server.view.entities.SpeedUpView;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public  class PowerDownModel extends EntityModel {
    public enum PowerDownType {
        SpeedDown,
        BombRadDown,
        BombsDown,
        RandomDown,
    }

    public static final float WIDTH = SpeedDownView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = SpeedDownView.HEIGHT * PIXEL_TO_METER;

    private PowerDownType type;

    public PowerDownModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.type = PowerDownType.values()[random.nextInt(PowerDownType.values().length)];
    }

    public PowerDownType getType(){
        return type;
    }

}
