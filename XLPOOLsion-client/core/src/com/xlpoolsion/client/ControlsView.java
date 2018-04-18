package com.xlpoolsion.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.xlpoolsion.client.networking.NetworkManager;
import com.xlpoolsion.common.Message;

import java.io.IOException;

public class ControlsView extends ScreenAdapter {
    private XLPOOLsionClient xlpooLsionClient;

    private Table rootTable;
    private Stage stage;

    private Label infoLbl;

    private Label ipLabel;
    private TextField ipField;
    private Label portLabel;
    private TextField portField;
    private TextButton connectButton;

    private Touchpad touchpad;

    private NetworkManager networkManager;

    public ControlsView(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;

        createGUIItems();

        stage = new Stage(new ScreenViewport(), xlpooLsionClient.getBatch());
        Gdx.input.setInputProcessor(stage);

        rootTable = new Table();
        rootTable.add(ipLabel);
        rootTable.add(ipField);
        rootTable.add(connectButton).height(connectButton.getMaxHeight()).right();
        rootTable.row();
        rootTable.add(portLabel);
        rootTable.add(portField);
        rootTable.row();
        //rootTable.add(infoLbl);
        rootTable.row();
        rootTable.add(touchpad).left();

        //rootTable.center();

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

    private void createGUIItems() {
        Label.LabelStyle simpleLblStyle = new Label.LabelStyle();
        simpleLblStyle.fontColor = Color.WHITE;
        //Using default font atm
        simpleLblStyle.font = new BitmapFont();

        infoLbl = new Label("Init text", simpleLblStyle);
        infoLbl.setFontScale(4.0f);
        //infoLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2, 0);
        infoLbl.setPosition(300, 300, 0);

        ipLabel = new Label("IP:", simpleLblStyle);

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = new BitmapFont();
        tfStyle.fontColor = Color.WHITE;
        tfStyle.focusedFontColor = Color.RED;

        ipField = new TextField("", tfStyle);
        portLabel = new Label("Port:", simpleLblStyle);
        portField = new TextField("", tfStyle);

        TextButton.TextButtonStyle tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = new BitmapFont();
        tbStyle.fontColor = Color.WHITE;
        tbStyle.downFontColor = Color.GREEN;
        tbStyle.checkedFontColor = Color.BLUE;
        tbStyle.disabledFontColor = Color.GRAY;

        connectButton = new TextButton("Connect!", tbStyle);

        Touchpad.TouchpadStyle tpadStyle = new Touchpad.TouchpadStyle();
        touchpad = new Touchpad(4.0f, tpadStyle);

        ipLabel.setFontScale(3.0f);
        ipField.setScale(3.0f);
        portLabel.setFontScale(3.0f);
        portField.setScale(3.0f);
        connectButton.setScale(3.0f);

        touchpad.setScale(3.0f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
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

        /*
        System.out.println(String.format("gyro X: %.2f    gyro Y: %.2f    gyro Z: %.2f",
                Gdx.input.getGyroscopeX(), Gdx.input.getGyroscopeY(), Gdx.input.getGyroscopeZ()));
        */

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
