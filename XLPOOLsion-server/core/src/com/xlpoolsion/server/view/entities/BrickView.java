package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xlpoolsion.server.XLPOOLsionServer;

public class BrickView extends EntityView {

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    public BrickView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        Texture img = xlpooLsionServer.getAssetManager().get("Wall.png");
        Sprite temp_sprite = new Sprite(img);
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }
}
