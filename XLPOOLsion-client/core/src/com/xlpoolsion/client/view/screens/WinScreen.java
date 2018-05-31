package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;

public class WinScreen extends StageScreen {
    public WinScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    private static final float FRAME_TIME = 0.6f;
    private float stateTime = 0;
    private Image victoryImage;

    private Animation<Drawable> victoryAnim;

    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("Victory_white1.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Victory_white2.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("GreenBackground.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    private void createAnimation() {
        Drawable[] frames = new Drawable[2];

        for(int i = 0;i < frames.length;i++){
            frames[i] = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("Victory_white"+ (i+1) + ".png")));
        }

        victoryAnim = new Animation<Drawable>(FRAME_TIME, frames);
    }

    @Override
    protected void createGUI() {
        addBackground();
        createImageVictory();
        createAnimation();
    }

    private void addBackground() {
        Image background = new Image((Texture) xlpooLsionClient.getAssetManager().get("GreenBackground.png"));
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight());
        stage.addActor(background);
    }

    private void createImageVictory() {
        victoryImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("Victory_white1.png"));
        victoryImage.setWidth(stage.getWidth() * 0.3f);
        victoryImage.setHeight(stage.getHeight() * 0.4f);
        victoryImage.setPosition(stage.getWidth() * 0.5f, stage.getHeight() * 0.4f, Align.center);
        stage.addActor(victoryImage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        victoryImage.setDrawable(victoryAnim.getKeyFrame(stateTime,true));
    }
}
