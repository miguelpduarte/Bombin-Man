package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

public abstract class PlayerView extends EntityView {

    private static final float FRAME_TIME = 0.13f;

    public static final float WIDTH = 32;
    public static final float HEIGHT = 64;

    protected Animation<TextureRegion> upAnim;
    protected Animation<TextureRegion> rightAnim;
    protected Animation<TextureRegion> downAnim;
    protected Animation<TextureRegion> leftAnim;
    protected Animation<TextureRegion> deathAnim;
    protected Animation<TextureRegion> stunAnim;

    private float stateTime = 0;
    private float deathTime = 0;
    private float stunTime = 0;

    private boolean isMoving = false;
    private boolean isDying = false;
    private boolean isStunned = false;
    private PlayerModel.Orientation orientation;

    public PlayerView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        createAnimations(xlpooLsionServer);
        Sprite temp_sprite = new Sprite(downAnim.getKeyFrame(stateTime, true));
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }

    abstract void createAnimations(XLPOOLsionServer xlpooLsionServer);

    protected Animation<TextureRegion> createUpAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[0], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createRightAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[1], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createDownAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[2], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createLeftAnimation(TextureRegion[][] fullregion) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullregion[3], 0, frames, 0, 3);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    protected Animation<TextureRegion> createDeathAnimation(TextureRegion[][] deathregion) {
        TextureRegion[] frames = new TextureRegion[10];
        System.arraycopy(deathregion[24], 0, frames, 0, 10);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }
    protected Animation<TextureRegion> createStunAnimation(TextureRegion[][] deathregion) {
        TextureRegion[] frames = new TextureRegion[4];
        System.arraycopy(deathregion[0], 0, frames, 0, 4);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);
        isMoving = ((PlayerModel) model).isMoving();
        isDying = ((PlayerModel) model).isDying();
        isStunned = ((PlayerModel) model).isStunned();
        orientation = ((PlayerModel) model).getCurrentOrientation();
    }

    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(isDying){
            sprite.setRegion(deathAnim.getKeyFrame(deathTime, false));
            deathTime += Gdx.graphics.getDeltaTime();
        }else if(isStunned) {
            sprite.setRegion(stunAnim.getKeyFrame(stunTime, true));
            stunTime += Gdx.graphics.getDeltaTime();
        } else if(isMoving) {
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
