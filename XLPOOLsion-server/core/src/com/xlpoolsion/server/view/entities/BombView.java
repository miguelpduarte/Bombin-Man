package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.XLPOOLsionServer;

public class BombView extends EntityView {
    private static final float FRAME_TIME = 0.13f;

    private Animation<Texture> explodingAnimation;
    private float stateTime = 0f;

    public BombView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        explodingAnimation = createExplodingAnimation(xlpooLsionServer);
        return new Sprite(explodingAnimation.getKeyFrame(0f));
    }

    private Animation<Texture> createExplodingAnimation(XLPOOLsionServer xlpooLsionServer) {
        Texture texture1 = xlpooLsionServer.getAssetManager().get("bomb/Bomb_f01.png");
        Texture texture2 = xlpooLsionServer.getAssetManager().get("bomb/Bomb_f02.png");
        Texture texture3 = xlpooLsionServer.getAssetManager().get("bomb/Bomb_f03.png");

        return new Animation<Texture>(FRAME_TIME, new Texture[] {texture1, texture2, texture3});
    }

    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(explodingAnimation.getKeyFrame(stateTime, true));

        sprite.draw(batch);
    }
}
