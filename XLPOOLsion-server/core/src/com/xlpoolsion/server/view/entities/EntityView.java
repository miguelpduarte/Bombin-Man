package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;

import static com.xlpoolsion.server.controller.levels.BaseLevelController.LEVEL_HEIGHT;
import static com.xlpoolsion.server.controller.levels.BaseLevelController.LEVEL_WIDTH;
import static com.xlpoolsion.server.view.screens.GameScreen.*;

public abstract class EntityView {
    protected Sprite sprite;
    protected XLPOOLsionServer xlpooLsionServer;

    public EntityView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;
        sprite = createSprite(xlpooLsionServer);
    }

    protected abstract Sprite createSprite(XLPOOLsionServer xlpooLsionServer);

    public void update(EntityModel model) {
        //Centering the entity on the screen, based on the level width
        sprite.setCenter((model.getX() - (LEVEL_WIDTH/2))/PIXEL_TO_METER + ENTITY_VIEW_X_SHIFT, (model.getY() - (LEVEL_HEIGHT/2))/PIXEL_TO_METER + ENTITY_VIEW_Y_SHIFT);
        sprite.setRotation(model.getRotation());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
