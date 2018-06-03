package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

/**
 * An abstract view used to create the base for all players
 */
public abstract class PlayerView extends EntityView {

    public static final float FRAME_TIME = 0.13f;

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

    /**
     * Creates a view for the player
     * @param xlpooLsionServer The game this view belongs to
     */
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

    /**
     * Abstract method to create the animations for the player, different players will have different coloured animations
     * @param xlpooLsionServer The game these animations belong to
     */
    abstract void createAnimations(XLPOOLsionServer xlpooLsionServer);

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
