package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;

public class ExplosionView extends EntityView {
    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    public ExplosionView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        Texture img = xlpooLsionServer.getAssetManager().get("explosion.png");
        Sprite temp_sprite = new Sprite(img);
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
        sprite.setAlpha(/*0.2f + */ ((ExplosionModel) model).getTimeToDecay() / ExplosionModel.EXPLOSION_DECAY_TIME);
    }
}
