package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.client.networking.NetworkRouter;
import com.xlpoolsion.client.view.ButtonFactory;
import com.xlpoolsion.common.ClientToServerMessage;

/**
 * The screen showed when the main gameplay is on going, used to control the player
 */
public class ControlsScreen extends StageScreen {
    private Skin skin;

    private Touchpad touchpad;

    /**
     * Creates a control screen
     * @param xlpooLsionClient The game the screen belongs to
     */
    public ControlsScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    @Override
    protected void loadAssets() {
        xlpooLsionClient.getAssetManager().load("JoystickBack.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("JoystickButton.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb_down.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb_up.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    @Override
    protected void createGUI() {
        createTouchpad();
        createBombPlaceButton();
    }

    private void createBombPlaceButton() {
        Button bombButton = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb_up.png", "Bomb_down.png",
                stage.getWidth() * 0.8f, stage.getHeight() * 0.3f, stage.getHeight() * 0.4f, stage.getHeight() * 0.4f
        );
        bombButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NetworkRouter.getInstance().sendToServer(new ClientToServerMessage(ClientToServerMessage.MessageType.PRESSED_PLACE_BOMB));
            }
        });
        stage.addActor(bombButton);
    }

    private void createTouchpad() {
        Touchpad.TouchpadStyle tpadStyle = new Touchpad.TouchpadStyle();
        TextureRegionDrawable trd1 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("JoystickBack.png")));
        TextureRegionDrawable trd2 = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("JoystickButton.png")));
        tpadStyle.background = trd1;
        tpadStyle.knob = trd2;
        touchpad = new Touchpad(2.0f, tpadStyle);
        touchpad.setPosition(stage.getWidth() * 0.2f, stage.getHeight() * 0.2f, Align.center);
        touchpad.setWidth(stage.getHeight() * 0.5f);
        touchpad.setHeight(stage.getHeight() * 0.5f);
        touchpad.setVisible(true);

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

    /**
     * Changes screen based on the current state
     * @param delta The time passed
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        sendInputMessages();

        switch (GameController.getInstance().getCurrentState()) {
            case LOST_CONNECTION:
                xlpooLsionClient.setScreen(new LostConnectionScreen(xlpooLsionClient));
                break;
            case WON:
                xlpooLsionClient.setScreen(new WinScreen(xlpooLsionClient));
                break;
            case LOST:
                xlpooLsionClient.setScreen(new LoseScreen(xlpooLsionClient));
                break;
        }

        if(GameController.getInstance().isStunned()) {
            Gdx.input.vibrate(100);
        } else {
            Gdx.input.cancelVibrate();
        }
    }

    private long lastInputTime_ms = System.currentTimeMillis();
    private static final long MSG_SEND_THRESHOLD_MS = 30;

    private void sendInputMessages() {
        if(System.currentTimeMillis() - lastInputTime_ms > MSG_SEND_THRESHOLD_MS) {
            sendTouchpadMessages();
            sendPhoneShakeMessages();
        }
    }

    private static final float KNOB_THRESHOLD = 0.2f;
    private static final float KNOB_SCALE = 1.4f;

    private void sendTouchpadMessages() {
        //Scaling up the movement so it feels more responsive but not letting it go over 1 (or -1, depending in the direction - signal of original value)
        float x = touchpad.getKnobPercentX() * KNOB_SCALE;
        float y = touchpad.getKnobPercentY() * KNOB_SCALE;
        if(Math.abs(x) < KNOB_THRESHOLD) {
            x = 0;
        }
        if(Math.abs(y) < KNOB_THRESHOLD) {
            y = 0;
        }
        if(Math.abs(x) > 1) {
            x = Math.signum(x);
        }
        if(Math.abs(y) > 1) {
            y = Math.signum(y);
        }
        //System.out.println("X: " + touchpad.getKnobPercentX());
        //System.out.println("Y: " + touchpad.getKnobPercentY());
        Vector2 vec = new Vector2(x, y);
        NetworkRouter.getInstance().sendToServer(new ClientToServerMessage(ClientToServerMessage.MessageType.PLAYER_MOVE, vec));
        lastInputTime_ms = System.currentTimeMillis();
    }

    private void sendPhoneShakeMessages() {
        if(GameController.getInstance().isStunned() && isShakingPhone()) {
            NetworkRouter.getInstance().sendToServer(new ClientToServerMessage(ClientToServerMessage.MessageType.CONTROLLER_SHAKE));
        }
    }

    private static final float SHAKE_THRESHOLD = 10.0f;

    private boolean isShakingPhone() {
        return Math.abs(Gdx.input.getGyroscopeY()) > SHAKE_THRESHOLD;
    }
}
