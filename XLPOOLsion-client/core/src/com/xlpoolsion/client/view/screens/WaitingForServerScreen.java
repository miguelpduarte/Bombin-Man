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

public class WaitingForServerScreen extends StageScreen {
    public WaitingForServerScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    private static final float FRAME_TIME = 0.2f;
    private float stateTime = 0;
    private Image loadingImage;

    private Animation<Drawable> loadingAnim;
    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("Loading-1.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Loading-2.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Loading-3.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Loading-4.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Loading-5.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("WaitingForServer.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }
    private void createAnimation() {
        Drawable[] frames = new Drawable[5];

        for(int i = 0;i < frames.length;i++){
            frames[i] = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("Loading-"+ (i+1) + ".png")));
        }

        loadingAnim = new Animation<Drawable>(FRAME_TIME, frames);
    }
    @Override
    protected void createGUI() {
        createImageLoading();
        createAnimation();
        createTextImage();
    }

    private void createTextImage() {
        Image textImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("WaitingForServer.png"));
        textImage.setWidth(stage.getWidth() * 0.6f);
        textImage.setHeight(stage.getHeight() * 0.2f);
        textImage.setPosition(stage.getWidth() * 0.5f, stage.getHeight() * 0.8f, Align.center);
        stage.addActor(textImage);
    }

    private void createImageLoading() {
        loadingImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("Loading-1.png"));
        loadingImage.setWidth(stage.getHeight() * 0.2f);
        loadingImage.setHeight(stage.getHeight() * 0.2f);
        loadingImage.setPosition(stage.getWidth() * 0.5f, stage.getHeight() * 0.5f, Align.center);
        stage.addActor(loadingImage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        loadingImage.setDrawable(loadingAnim.getKeyFrame(stateTime,true));
        switch (GameController.getInstance().getCurrentState()) {
            case PLAYING:
                //Game started
                xlpooLsionClient.setScreen(new ControlsScreen(xlpooLsionClient));
                break;
            case SERVER_FULL:
                System.out.println("Server full! Screen is WIP");
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
                //Temporary because screen is not yet created (Should be done when pressing back button in server_full screen)
                GameController.getInstance().resetState();
                break;
            case LOST_CONNECTION:
                System.out.println("Lost connection to server!!");
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
                break;
        }
    }
}
