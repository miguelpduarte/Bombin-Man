package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;

public class ExplosionView extends EntityView {
    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;
    private static final float FRAME_TIME = 0.2f;

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

    void createAnimations(XLPOOLsionServer xlpooLsionServer) {
        Texture alltextures = xlpooLsionServer.getAssetManager().get("ExplosionAnimation.png");
        TextureRegion[][] fullregion = TextureRegion.split(alltextures, 16, 16);

        centerAnim = createCenterAnimation(fullregion);
        verticalAnim = createVerticalAnimation(fullregion);
        horizontalAnim = createHorizontalAnimation(fullregion);
    }

    protected Animation<TextureRegion> createCenterAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(fullregion[2], 0, frames, 0, 5);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createVerticalAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(fullregion[1], 0, frames, 0, 5);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createHorizontalAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(fullregion[0], 0, frames, 0, 5);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    @Override
    public void update(EntityModel model) {
        direction = ((ExplosionModel)model).getDirection();
        super.update(model);
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
