package com.xlpoolsion.server.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.PlayerModel;

public class PlayerView {

    private static final float FRAME_TIME = 0.15f;

    private Sprite sprite;
    //To change to respective animations
    private Animation<TextureRegion> upAnim;
    private Animation<TextureRegion> rightAnim;
    private Animation<TextureRegion> downAnim;
    private Animation<TextureRegion> leftAnim;

    private float stateTime = 0;


    TextureRegion regionUp;
    TextureRegion regionRight;
    TextureRegion regionDown;
    TextureRegion regionLeft;

    public PlayerView(XLPOOLsionServer xlpooLsionServer) {
        Texture alltextures = xlpooLsionServer.getAssetManager().get("Bomberman_sprite.png");
        TextureRegion[][] fullregion = TextureRegion.split(alltextures, 16, 32);

        regionUp = fullregion[0][0];
        regionRight = fullregion[1][0];
        regionDown = fullregion[2][0];
        regionLeft = fullregion[3][0];

        upAnim = createUpAnimation(fullregion);
        rightAnim = createRightAnimation(fullregion);
        //downAnim;
        leftAnim = createLeftAnimation(fullregion);

        sprite = new Sprite(regionDown);
    }

    private Animation<TextureRegion> createUpAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[0], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    private Animation<TextureRegion> createRightAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[1], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    private Animation<TextureRegion> createLeftAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[3], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    public void draw(float delta, SpriteBatch batch, PlayerModel playerModel) {
        stateTime += delta;

        switch (playerModel.getCurrentOrientation()) {
            case UP:
                //sprite.setRegion(regionUp);
                sprite.setRegion(upAnim.getKeyFrame(stateTime, true));
                break;
            case RIGHT:
                //sprite.setRegion(regionRight);
                sprite.setRegion(rightAnim.getKeyFrame(stateTime, true));
                break;
            case DOWN:
                sprite.setRegion(regionDown);
                break;
            case LEFT:
                //sprite.setRegion(regionLeft);
                sprite.setRegion(leftAnim.getKeyFrame(stateTime, true));
                break;
        }

        sprite.setPosition(playerModel.getX(), playerModel.getY());

        sprite.draw(batch);
    }
}
