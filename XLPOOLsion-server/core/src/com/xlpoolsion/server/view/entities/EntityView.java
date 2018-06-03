package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;

import static com.xlpoolsion.server.controller.levels.BaseLevelController.LEVEL_HEIGHT;
import static com.xlpoolsion.server.controller.levels.BaseLevelController.LEVEL_WIDTH;
import static com.xlpoolsion.server.view.screens.GameScreen.*;

/**
 * An abstract view capable of holding a sprite with a certain
 * position and rotation.
 *
 * This view is able to update its data based on a entity model.
 */
public abstract class EntityView {
    protected Sprite sprite;
    protected XLPOOLsionServer xlpooLsionServer;

    /**
     * Creates a View belonging to the game
     * @param xlpooLsionServer The game this view belongs to
     */
    public EntityView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;
        sprite = createSprite(xlpooLsionServer);
    }

    /**
     * Abstract method that creates the view sprite. Concrete
     * implementation extends this method to create their
     * own sprites.
     *
     * @param xlpooLsionServer  the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this view.
     */
    protected abstract Sprite createSprite(XLPOOLsionServer xlpooLsionServer);

    /**
     * Updates this view based on a certain model.
     *
     * @param model the model used to update this view
     */
    public void update(EntityModel model) {
        //Centering the entity on the screen, based on the level width
        sprite.setCenter((model.getX() - (LEVEL_WIDTH/2))/PIXEL_TO_METER + ENTITY_VIEW_X_SHIFT, (model.getY() - (LEVEL_HEIGHT/2))/PIXEL_TO_METER + ENTITY_VIEW_Y_SHIFT);
        sprite.setRotation(model.getRotation());
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
