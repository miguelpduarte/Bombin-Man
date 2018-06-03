package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;

/**
 * A view representing an explosion
 */
public class ExplosionView extends EntityView {
    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;
    public static final float FRAME_TIME = 0.2f;

    /**
     * Creates an explosion view
     * @param xlpooLsionServer The game this view belongs to
     */
    public ExplosionView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    protected Animation<TextureRegion> centerAnim;
    protected Animation<TextureRegion> verticalAnim;
    protected Animation<TextureRegion> horizontalAnim;
    private ExplosionModel.Direction direction;
    private float stateTime = 0;

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        createAnimations(xlpooLsionServer);
        Sprite temp_sprite = new Sprite(centerAnim.getKeyFrame(stateTime, false));
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }

    /**
     * Creates the animations for the different types of explosions
     * @param xlpooLsionServer The game these animations belong to
     */
    void createAnimations(XLPOOLsionServer xlpooLsionServer) {
        centerAnim = xlpooLsionServer.getTextureManager().getExplosionAnimation(ExplosionModel.Direction.Center);
        verticalAnim = xlpooLsionServer.getTextureManager().getExplosionAnimation(ExplosionModel.Direction.Vertical);
        horizontalAnim = xlpooLsionServer.getTextureManager().getExplosionAnimation(ExplosionModel.Direction.Horizontal);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
        direction = ((ExplosionModel)model).getDirection();
        if(((ExplosionModel) model).getTimeToDecay() < 0) {
            sprite.setAlpha(0f);
        } else {
            sprite.setAlpha(((ExplosionModel) model).getTimeToDecay() / ExplosionModel.EXPLOSION_DECAY_TIME);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        switch (direction){
            case Center:
                sprite.setRegion(centerAnim.getKeyFrame(stateTime,false));
                break;
            case Vertical:
                sprite.setRegion(verticalAnim.getKeyFrame(stateTime,false));
                break;
            case Horizontal:
                sprite.setRegion(horizontalAnim.getKeyFrame(stateTime,false));
                break;
        }

        stateTime += Gdx.graphics.getDeltaTime();
        sprite.draw(batch);
    }
}
