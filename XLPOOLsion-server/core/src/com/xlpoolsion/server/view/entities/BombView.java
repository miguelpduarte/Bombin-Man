package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;

public class BombView extends EntityView {
    private static final float FRAME_TIME = 0.25f;

    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    private Animation<TextureRegion> explodingAnimation;
    private float stateTime = 0f;

    public BombView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        explodingAnimation = createExplodingAnimation(xlpooLsionServer);

        Sprite temp_sprite = new Sprite(explodingAnimation.getKeyFrame(0f));
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }

    private Animation<TextureRegion> createExplodingAnimation(XLPOOLsionServer xlpooLsionServer) {
        Texture alltextures = xlpooLsionServer.getAssetManager().get("Bomb_sprite_transparent.png");
        TextureRegion[][] fullregion = TextureRegion.split(alltextures, 16, 16);

        TextureRegion[] bomb_anim_frames =  new TextureRegion[4];
        System.arraycopy(fullregion[0], 0, bomb_anim_frames, 0, 4);

        return new Animation<TextureRegion>(FRAME_TIME, bomb_anim_frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(explodingAnimation.getKeyFrame(stateTime, true));
        sprite.draw(batch);
    }
}
