package com.xlpoolsion.server.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.PlayerModel;

import static com.xlpoolsion.server.view.GameView.PIXEL_TO_METER;

public class PlayerView {

    private static final float FRAME_TIME = 0.13f;

    private Sprite sprite;
    private Animation<TextureRegion> upAnim;
    private Animation<TextureRegion> rightAnim;
    private Animation<TextureRegion> downAnim;
    private Animation<TextureRegion> leftAnim;

    private float stateTime = 0;

    private boolean isMoving = false;
    private PlayerModel.Orientation orientation;

    public PlayerView(XLPOOLsionServer xlpooLsionServer) {
        Texture alltextures = xlpooLsionServer.getAssetManager().get("Bomberman_sprite.png");
        TextureRegion[][] fullregion = TextureRegion.split(alltextures, 16, 32);

        upAnim = createUpAnimation(fullregion);
        rightAnim = createRightAnimation(fullregion);
        downAnim = createDownAnimation(fullregion);
        leftAnim = createLeftAnimation(fullregion);

        sprite = new Sprite(downAnim.getKeyFrame(stateTime, true));
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

    private Animation<TextureRegion> createDownAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[2], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    private Animation<TextureRegion> createLeftAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[3], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    public void update(PlayerModel playerModel) {
        sprite.setCenter(playerModel.getX() / PIXEL_TO_METER, playerModel.getY() / PIXEL_TO_METER);
        isMoving = playerModel.isMoving();
        orientation = playerModel.getCurrentOrientation();
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        if(isMoving) {
            setMovingAnimationFrame();
        } else {
            setStillAnimationFrame();
        }

        sprite.draw(batch);
    }

    private void setStillAnimationFrame() {
        switch (orientation) {
            case UP:
                sprite.setRegion(upAnim.getKeyFrame(0f));
                break;
            case RIGHT:
                sprite.setRegion(rightAnim.getKeyFrame(0f));
                break;
            case DOWN:
                sprite.setRegion(downAnim.getKeyFrame(0f));
                break;
            case LEFT:
                sprite.setRegion(leftAnim.getKeyFrame(0f));
                break;
        }
    }

    private void setMovingAnimationFrame() {
        switch (orientation) {
            case UP:
                sprite.setRegion(upAnim.getKeyFrame(stateTime, true));
                break;
            case RIGHT:
                sprite.setRegion(rightAnim.getKeyFrame(stateTime, true));
                break;
            case DOWN:
                sprite.setRegion(downAnim.getKeyFrame(stateTime, true));
                break;
            case LEFT:
                sprite.setRegion(leftAnim.getKeyFrame(stateTime, true));
                break;
        }
    }
}
