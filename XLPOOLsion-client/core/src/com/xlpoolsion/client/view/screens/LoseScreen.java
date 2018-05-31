package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;

public class LoseScreen extends StageScreen {
    public LoseScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    private static final float FRAME_TIME = 0.4f;
    private float stateTime = 0;
    private Image losingImage;

    private Animation<Drawable> losingAnim;

    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("Losing1.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Losing2.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Losing3.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("RedBackground.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    private void createAnimation() {
        Drawable[] frames = new Drawable[3];

        for(int i = 0;i < frames.length;i++){
            frames[i] = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("Losing"+ (i+1) + ".png")));
        }

        losingAnim = new Animation<Drawable>(FRAME_TIME, frames);
    }

    @Override
    protected void createGUI() {
        addBackground();
        createLosingImage();
        createAnimation();
    }

    private void addBackground() {
        Image background = new Image((Texture) xlpooLsionClient.getAssetManager().get("RedBackground.png"));
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight());
        stage.addActor(background);
    }

    private void createLosingImage() {
        losingImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("Losing1.png"));
        losingImage.setWidth(stage.getWidth() * 0.3f);
        losingImage.setHeight(stage.getHeight() * 0.4f);
        losingImage.setPosition(stage.getWidth() * 0.5f, stage.getHeight() * 0.4f, Align.center);
        stage.addActor(losingImage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        losingImage.setDrawable(losingAnim.getKeyFrame(stateTime,true));
    }
}
