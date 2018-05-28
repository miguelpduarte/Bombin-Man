package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.view.ButtonFactory;

public class MainMenuScreen extends StageScreen {
    public MainMenuScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
        loadAssets();
        createGUI();
    }

    private void loadAssets() {
        xlpooLsionClient.getAssetManager().load("badlogic.jpg", Texture.class);
        xlpooLsionClient.getAssetManager().load("black_circle_50px.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    private void createGUI() {
        createConnectButton();
    }

    private void createConnectButton() {
        Button connectButton = ButtonFactory.makeButton(
                xlpooLsionClient, "black_circle_50px.png", "badlogic.jpg", stage.getWidth() * 0.5f, stage.getHeight() * 0.8f,
                stage.getWidth() * 0.4f, stage.getHeight() * 0.1f);

        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                xlpooLsionClient.setScreen(new ConnectScreen(xlpooLsionClient));
            }
        });
        stage.addActor(connectButton);
    }
}
