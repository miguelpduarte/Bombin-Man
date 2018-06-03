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
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.client.view.ButtonFactory;

/**
 * Screen that is shown when the server is full
 */
public class ServerFullScreen extends StageScreen {
    /**
     * Creates a server full screen
     * @param xlpooLsionClient The game this screen belongs to
     */
    public ServerFullScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    private static final float FRAME_TIME = 0.25f;
    private float stateTime = 0;
    private Image serverFullImage;

    private Animation<Drawable> ServerFullAnim;

    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("ServerFull.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("ServerFull2.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("back-button-md.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("ServerFullBackground.jpg", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }
    private void createAnimation() {
        Drawable drawable = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("ServerFull.png")));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("ServerFull2.png")));
        Drawable[] frames = new Drawable[8];
        frames[0] = drawable;
        frames[1] = drawable2;
        frames[2] = drawable;
        frames[3] = drawable;
        frames[4] = drawable;
        frames[5] = drawable;
        frames[6] = drawable;
        frames[7] = drawable;

        ServerFullAnim = new Animation<Drawable>(FRAME_TIME, frames);
    }
    @Override
    protected void createGUI() {
        addBackground();
        createImageVictory();
        createAnimation();
        createBackButton();
    }

    private void addBackground() {
        Image background = new Image((Texture) xlpooLsionClient.getAssetManager().get("ServerFullBackground.jpg"));
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight());
        stage.addActor(background);
    }

    private void createImageVictory() {
        serverFullImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("ServerFull.png"));
        serverFullImage.setWidth(stage.getWidth());
        serverFullImage.setHeight(stage.getHeight());
        stage.addActor(serverFullImage);
    }

    private void createBackButton() {
        Button backButton = ButtonFactory.makeButton(
                xlpooLsionClient, "back-button-md.png", "back-button-md.png", stage.getWidth() * 0.8f, stage.getHeight() * 0.15f,
                stage.getWidth() * 0.15f, stage.getHeight() * 0.15f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().resetState();
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
            }
        });
        stage.addActor(backButton);
    }

    /**
     * Updates the animation
     * @param delta the time passed
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        serverFullImage.setDrawable(ServerFullAnim.getKeyFrame(stateTime,true));
    }
}
