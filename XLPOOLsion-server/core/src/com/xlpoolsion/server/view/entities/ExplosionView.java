package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xlpoolsion.server.XLPOOLsionServer;

public class ExplosionView extends EntityView {
    public ExplosionView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        Texture img = xlpooLsionServer.getAssetManager().get("explosion.png");
        return new Sprite(img);
    }
}
