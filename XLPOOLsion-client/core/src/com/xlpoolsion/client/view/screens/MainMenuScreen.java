package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.view.ButtonFactory;

public class MainMenuScreen extends StageScreen {
    public MainMenuScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("button_connect_up.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_connect_down.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_exit_up.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_exit_down.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("BackgroundMainmenu.jpg", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    protected void createGUI() {
        addBackground();
        createConnectButton();
        createExitButton();
    }

    private void addBackground() {
        Image background = new Image(new Texture("BackgroundMainmenu.jpg"));
        background.setSize(stage.getWidth(),stage.getHeight());
        stage.addActor(background);
    }

    private void createExitButton() {
        Button exitButton = ButtonFactory.makeButton(
                xlpooLsionClient, "button_exit_up.png", "button_exit_down.png", stage.getWidth() * 0.5f, stage.getHeight() * 0.35f,
                stage.getWidth() * 0.3f, stage.getHeight() * 0.1f
        );
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    private void createConnectButton() {
        Button connectButton = ButtonFactory.makeButton(
                xlpooLsionClient, "button_connect_up.png", "button_connect_down.png", stage.getWidth() * 0.5f, stage.getHeight() * 0.55f,
                stage.getWidth() * 0.3f, stage.getHeight() * 0.1f);

        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                xlpooLsionClient.setScreen(new ConnectScreen(xlpooLsionClient));

            }
        });
        stage.addActor(connectButton);
    }
}
