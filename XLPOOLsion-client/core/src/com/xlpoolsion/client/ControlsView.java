package com.xlpoolsion.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.xlpoolsion.client.networking.Connection;
import com.xlpoolsion.client.networking.NetworkRouter;
import com.xlpoolsion.common.Message;

import java.io.IOException;

public class ControlsView extends ScreenAdapter {
    private XLPOOLsionClient xlpooLsionClient;

    private Table rootTable;
    private Stage stage;

    private Skin skin;
    private Label infoLbl;

    private Label ipLabel;
    private TextField ipField;
    private Label portLabel;
    private TextField portField;
    private TextButton connectButton;

    private Touchpad touchpad;
    private Texture img;

    public ControlsView(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;

        this.xlpooLsionClient.getAssetManager().load("badlogic.jpg", Texture.class);
        this.xlpooLsionClient.getAssetManager().finishLoading();

        img = this.xlpooLsionClient.getAssetManager().get("badlogic.jpg");

        createGUIItems();
        touchpad.setPosition(400, 400);

        //stage = new Stage(new ScreenViewport(), xlpooLsionClient.getBatch());
        //Gdx.input.setInputProcessor(stage);

        /*
        rootTable = new Table();
        //rootTable.setFillParent(true);
        //stage.addActor(rootTable);

        //////
        rootTable.add(ipLabel).left();
        rootTable.add(ipField).left();
        rootTable.add(connectButton).height(connectButton.getMaxHeight()).padLeft(5f);
        rootTable.row();
        rootTable.add(portLabel);
        rootTable.add(portField);
        rootTable.row();
        //rootTable.add(infoLbl)

        rootTable.pack();
        */

        //Debug lines
        //rootTable.setDebug(true);

        /*
        try {
            NetworkRouter.getInstance().setConnection(new Connection("192.168.1.8", 9876));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    private void createGUIItems() {
        skinInit();

        infoLbl = new Label("Init text", skin);
        //infoLbl.setFontScale(4.0f);

        ipLabel = new Label("IP:", skin);

        ipField = new TextField("", skin);
        portLabel = new Label("Port:", skin);
        portField = new TextField("", skin);

        connectButton = new TextButton("Connect!", skin);

        Touchpad.TouchpadStyle tpadStyle = new Touchpad.TouchpadStyle();
        tpadStyle.background = skin.newDrawable("white", Color.YELLOW);
        tpadStyle.knob = skin.newDrawable("white", Color.GOLD);
        touchpad = new Touchpad(4.0f, tpadStyle);

        /*
        ipLabel.setFontScale(3.0f);
        ipField.setScale(3.0f);
        portLabel.setFontScale(3.0f);
        portField.setScale(3.0f);
        connectButton.setScale(3.0f);

        touchpad.setScale(3.0f);
        */
    }

    private void skinInit() {
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("white", Color.CORAL);
        textButtonStyle.checked = skin.newDrawable("white", Color.MAGENTA);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = skin.getFont("default");
        tfStyle.fontColor = Color.WHITE;
        tfStyle.focusedFontColor = Color.RED;
        tfStyle.background = skin.newDrawable("white", Color.GRAY);
        skin.add("default", tfStyle);

        Label.LabelStyle simpleLblStyle = new Label.LabelStyle();
        simpleLblStyle.fontColor = Color.WHITE;
        simpleLblStyle.font = skin.getFont("default");
        skin.add("default", simpleLblStyle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        xlpooLsionClient.getBatch().begin();
        touchpad.toFront();
        touchpad.draw(xlpooLsionClient.getBatch(), 1.0f);
        xlpooLsionClient.getBatch().draw(img, 600, 150);
        xlpooLsionClient.getBatch().end();

        /*
        stage.act(delta);
        stage.draw();
        */



        if(Gdx.input.isTouched()) {
            touchpad.setPosition(Gdx.input.getX(), Gdx.input.getY());
            //NetworkRouter.getInstance().sendToServer(new Message(Message.MessageType.TEST_MESSAGE));
        }

        if(isShakingPhone()) {
            Gdx.input.vibrate(250);
            NetworkRouter.getInstance().sendToServer(new Message(Message.MessageType.CONTROLLER_SHAKE));
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
        //stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        //stage.dispose();
    }
}
