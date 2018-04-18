package com.xlpoolsion.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.xlpoolsion.client.networking.NetworkManager;
import com.xlpoolsion.common.Message;

import java.io.IOException;

public class ControlsView extends ScreenAdapter {
    private Label infoLbl;
    private NetworkManager networkManager;
    private XLPOOLsionClient xlpooLsionClient;

    private Table rootTable;
    private Stage stage;

    public ControlsView(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        //Using default font atm
        style.font = new BitmapFont();

        infoLbl = new Label("Init text", style);
        infoLbl.setFontScale(4.0f);
        //infoLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2, 0);
        infoLbl.setPosition(300, 300, 0);

        stage = new Stage(new ScreenViewport(), xlpooLsionClient.getBatch());
        Gdx.input.setInputProcessor(stage);

        rootTable = new Table();
        rootTable.add(infoLbl);
        rootTable.row();

        rootTable.center();

        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        //Debug lines
        rootTable.setDebug(true);

        /*
        try {
			networkManager = new NetworkManager("192.168.1.17", 9021);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        */
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if(isShakingPhone()) {
            Gdx.input.vibrate(250);
            /*
            try {
                networkManager.sendMessage(new Message(5, 6.9f, "wow what a message!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }
    }

    private static final float SHAKE_THRESHOLD = 10.0f;

    private boolean isShakingPhone() {

        infoLbl.setText(String.format("gyro X: %.2f    gyro Y: %.2f    gyro Z: %.2f",
                Gdx.input.getGyroscopeX(), Gdx.input.getGyroscopeY(), Gdx.input.getGyroscopeZ()));


        System.out.println(String.format("gyro X: %.2f    gyro Y: %.2f    gyro Z: %.2f",
                Gdx.input.getGyroscopeX(), Gdx.input.getGyroscopeY(), Gdx.input.getGyroscopeZ()));

        return Math.abs(Gdx.input.getGyroscopeY()) > SHAKE_THRESHOLD;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
