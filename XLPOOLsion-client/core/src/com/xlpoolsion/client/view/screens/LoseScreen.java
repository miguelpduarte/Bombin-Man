package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.view.ButtonFactory;

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
        xlpooLsionClient.getAssetManager().load("back-button-md.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Game_Over_background.png", Texture.class);
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
        createBackButton();
    }

    private void addBackground() {
        Image background = new Image((Texture) xlpooLsionClient.getAssetManager().get("Game_Over_background.png"));
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

    private void createBackButton() {
        Button backButton = ButtonFactory.makeButton(
                xlpooLsionClient, "back-button-md.png", "back-button-md.png", stage.getWidth() * 0.8f, stage.getHeight() * 0.15f,
                stage.getWidth() * 0.15f, stage.getHeight() * 0.15f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        losingImage.setDrawable(losingAnim.getKeyFrame(stateTime,true));
    }
}
