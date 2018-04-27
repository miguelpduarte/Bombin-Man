package com.xlpoolsion.client.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xlpoolsion.client.XLPOOLsionClient;

public class ConnectView extends StageView {
    public ConnectView(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
        //TODO: Fix the rotation coordinates...
        //super.setPortrait();

        loadAssets();
        createElements();
    }

    private void loadAssets() {
        xlpooLsionClient.getAssetManager().load("joystick_bomb_200px.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("joystick_player_face_100px.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    private void createElements() {
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        TextureRegionDrawable trd1 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("joystick_bomb_200px.png")));
        TextureRegionDrawable trd2 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("joystick_player_face_100px.png")));
        buttonStyle.down = trd1;
        buttonStyle.up = trd2;

        Button but = new Button(buttonStyle);

        but.setPosition(stage.getViewport().getScreenWidth() * 0.9f, stage.getViewport().getScreenHeight() * 0.9f, 1);
        stage.addActor(but);
    }
}
