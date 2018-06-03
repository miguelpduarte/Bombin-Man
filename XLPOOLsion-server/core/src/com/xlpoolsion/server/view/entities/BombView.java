package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;

/**
 * A view representing a bomb
 */
public class BombView extends EntityView {
    public static final float FRAME_TIME = 0.25f;

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    private Animation<TextureRegion> explodingAnimation;
    private float stateTime = 0f;

    /**
     * Creates a bomb view
     * @param xlpooLsionServer The game this view belongs to
     */
    public BombView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        //This way no duplicate animations are created
        explodingAnimation = xlpooLsionServer.getTextureManager().getBombAnimation();

        Sprite temp_sprite = new Sprite(explodingAnimation.getKeyFrame(0f));
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }

    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(explodingAnimation.getKeyFrame(stateTime, true));
        sprite.draw(batch);
    }
}
