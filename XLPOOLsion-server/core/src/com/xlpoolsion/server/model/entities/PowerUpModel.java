package com.xlpoolsion.server.model.entities;


import com.xlpoolsion.server.view.entities.SpeedUpView;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public  class PowerUpModel extends EntityModel {
    public enum PowerUpType {
        SpeedUp,
        //SpeedDown,
        BombRadUp,
        //BombRadDown,

    }

    public static final float WIDTH = SpeedUpView.WIDTH * PIXEL_TO_METER;
    public static final float HEIGHT = SpeedUpView.HEIGHT * PIXEL_TO_METER;

    private PowerUpType type;

    public PowerUpModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.type = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
    }

    public PowerUpType getType(){
        return type;
    }

}
