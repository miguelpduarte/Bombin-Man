package com.xlpoolsion.client.view;

import com.badlogic.gdx.Gdx;
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
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.networking.Connection;
import com.xlpoolsion.client.networking.NetworkRouter;
import com.xlpoolsion.common.Message;

import java.io.IOException;

public class ControlsView extends StageView {
    private Skin skin;

    private Touchpad touchpad;

    public ControlsView(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);

        createGUIItems();

        /*
        try {
            NetworkRouter.getInstance().setConnection(new Connection("192.168.2.197", 9876));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    private void createGUIItems() {
        skinInit();

        createTouchpad();
    }

    private void createTouchpad() {
        Touchpad.TouchpadStyle tpadStyle = new Touchpad.TouchpadStyle();
        tpadStyle.background = skin.newDrawable("white", Color.YELLOW);
        tpadStyle.knob = skin.newDrawable("white", Color.GOLD);
        touchpad = new Touchpad(4.0f, tpadStyle);
        touchpad.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        stage.addActor(touchpad);
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

    /*
    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            //touchpad.setPosition(Gdx.input.getX(), Gdx.input.getY());
            //NetworkRouter.getInstance().sendToServer(new Message(Message.MessageType.TEST_MESSAGE));
            if(conn == null) {
                System.out.println("Conn is null!");
            } else {
                conn.sendMessage(new Message(Message.MessageType.TEST_MESSAGE));
            }
        }


        if(isShakingPhone()) {
            Gdx.input.vibrate(250);
            if(conn == null) {
                System.out.println("Conn is null!");
            } else {
                conn.sendMessage(new Message(Message.MessageType.CONTROLLER_SHAKE));
            }
            //NetworkRouter.getInstance().sendToServer(new Message(Message.MessageType.CONTROLLER_SHAKE));
        }
    }
    */

    private static final float SHAKE_THRESHOLD = 10.0f;

    private boolean isShakingPhone() {
        return Math.abs(Gdx.input.getGyroscopeY()) > SHAKE_THRESHOLD;
    }

    @Override
    public void dispose() {
        super.dispose();
        NetworkRouter.getInstance().endConnection();
    }
}
