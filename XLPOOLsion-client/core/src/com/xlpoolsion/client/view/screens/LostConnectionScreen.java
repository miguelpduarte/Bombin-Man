package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.client.view.ButtonFactory;

public class LostConnectionScreen extends StageScreen {
    public LostConnectionScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }


    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("LostConnection.jpg", Texture.class);
        xlpooLsionClient.getAssetManager().load("back-button-md.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    @Override
    protected void createGUI() {
        addBackground();
        createBackButton();
    }

    private void addBackground() {
        Image background = new Image((Texture) xlpooLsionClient.getAssetManager().get("LostConnection.jpg"));
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight());
        stage.addActor(background);
    }

    private void createBackButton() {
        Button backButton = ButtonFactory.makeButton(
                xlpooLsionClient, "back-button-md.png", "back-button-md.png", stage.getWidth() * 0.8f, stage.getHeight() * 0.2f,
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

}
