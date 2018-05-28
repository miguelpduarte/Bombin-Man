package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.networking.Connection;
import com.xlpoolsion.client.networking.NetworkRouter;

import java.io.IOException;

public class ConnectScreen extends StageScreen {
    private Table table;
    private Button connectButton;
    private Label addressLabel;
    private TextField addressText;
    private Skin skin;

    public ConnectScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);

        table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
        stage.addActor(table);

        table.setDebug(true);
        loadAssets();
        createElements();
    }

    private void loadAssets() {
        xlpooLsionClient.getAssetManager().load("joystick_bomb_200px.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("joystick_player_face_100px.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
        createSkin();
    }

    private void createSkin() {
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

    private void createElements() {
        createAddressDialog();
        createConnectButton();
        addElementsToTable();
    }

    private void addElementsToTable() {
        table.add(addressLabel).fill().top().center();
        table.add(addressText).prefWidth(800).prefHeight(200);
        table.row();
        table.add(connectButton).fill().right();
    }

    private void createAddressDialog() {
        addressLabel = new Label("Address:", skin);
        addressText = new TextField("", skin);
    }

    private void createConnectButton() {
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        TextureRegionDrawable trd1 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("joystick_bomb_200px.png")));
        TextureRegionDrawable trd2 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("joystick_player_face_100px.png")));
        buttonStyle.down = trd1;
        buttonStyle.up = trd2;

        connectButton = new Button(buttonStyle);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Attempting to connect to ip: " + addressText.getText());
                try {
                    NetworkRouter.getInstance().setConnection(new Connection(addressText.getText(), 9876));
                    xlpooLsionClient.setScreen(new ControlsScreen(xlpooLsionClient));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
