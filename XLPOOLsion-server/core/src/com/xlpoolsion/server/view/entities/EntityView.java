package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

public abstract class EntityView {
    protected Sprite sprite;

    public EntityView(XLPOOLsionServer xlpooLsionServer) {
        sprite = createSprite(xlpooLsionServer);
    }

    protected abstract Sprite createSprite(XLPOOLsionServer xlpooLsionServer);

    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation(model.getRotation());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
