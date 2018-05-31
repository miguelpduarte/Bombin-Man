package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.client.networking.Connection;
import com.xlpoolsion.client.networking.NetworkRouter;
import com.xlpoolsion.client.view.ButtonFactory;

import java.io.IOException;

public class ConnectScreen extends StageScreen {
    private Image textBoxImage;

    private static final int MAX_NUMBERS = 12;
    private Image[] ipNumbers;
    private int numberInsertIndex = 0;

    private Button[] keypadButtons;
    private Drawable[] ipNumberDrawables;
    private Drawable cleanBackgroundDrawable;

    private Button connectButton;
    private Button backButton;
    private Button eraseButton;

    //Create textures and store them

    private String connectIp = "";

    public ConnectScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        switch (GameController.getInstance().getCurrentState()) {
            case WAITING_FOR_SERVER:
                xlpooLsionClient.setScreen(new WaitingForServerScreen(xlpooLsionClient));
                break;
        }
    }

    @Override
    protected void loadAssets() {
        allocateArrays();
        xlpooLsionClient.getAssetManager().load("joystick_bomb_200px.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("joystick_player_face_100px.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-1.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-2.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-3.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-4.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-5.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-6.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-7.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-8.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-9.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("Bomb-0.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("TextBoxImage.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("CleanBackground.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("1Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("2Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("3Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("4Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("5Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("6Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("7Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("8Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("9Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("0Text.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_connect__Up.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_connect__down.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_backDOWN.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("button_backUP.png", Texture.class);
        xlpooLsionClient.getAssetManager().load("backspace_button.png", Texture.class);
        xlpooLsionClient.getAssetManager().finishLoading();
    }

    private void allocateArrays() {
        //Because StageScreen is a factory method, the arrays must be allocated before they are used in createGUI
        // and they can't be allocated in this subclass constructor or in the field declaration because that would
        // only run after the superclass constructor
        ipNumbers = new Image[MAX_NUMBERS];
        ipNumberDrawables = new Drawable[10];
        keypadButtons = new Button[10];
    }

    @Override
    protected void createGUI() {
        for(int i = 0; i < 10; ++i) {
            createKeypadButton(i);
        }
        createImageTextBox();
        createConnectButton();
        createBackButton();
        initializeIpNumbers();
        createEraseButton();
    }

    private void createKeypadButton(final int i) {
        float xpos;
        float ypos;
        if(i == 0){
            xpos = stage.getWidth() * 0.50f;
            ypos = stage.getHeight() * 0.20f;
        } else {
            xpos = stage.getWidth() * (0.40f + (((i-1)%3)*0.1f));
            ypos = stage.getHeight() * (0.65f - (((i - 1) / 3) * 0.15f));
        }
        keypadButtons[i] = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-" + i + ".png", "Bomb-" + i + ".png", xpos, ypos,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        keypadButtons[i].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateInput(i);
            }
        });

        stage.addActor(keypadButtons[i]);
    }

    private void initializeIpNumbers() {
        loadIpNumberDrawables();

        float imageWidth = stage.getHeight() * 0.04f;
        float imageHeight = stage.getHeight() * 0.1f;
        float imageStartX = textBoxImage.getX() + imageWidth;
        float imageStartY = textBoxImage.getY() + stage.getWidth() * 0.02f;

        for(int i = 0; i < 12; i++){
            ipNumbers[i] = new Image();
            ipNumbers[i].setPosition(imageStartX + (imageWidth * i), imageStartY, Align.center);
            ipNumbers[i].setDrawable(cleanBackgroundDrawable);
            ipNumbers[i].setSize(imageWidth, imageHeight);
            stage.addActor(ipNumbers[i]);
        }
    }

    private void loadIpNumberDrawables() {
        for(int i = 0; i < 10; ++i) {
            ipNumberDrawables[i] = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get(i + "Text.png")));
        }
        cleanBackgroundDrawable = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionClient.getAssetManager().get("CleanBackground.png")));
    }

    private void createImageTextBox() {
        textBoxImage = new Image((Texture) xlpooLsionClient.getAssetManager().get("TextBoxImage.png"));
        textBoxImage.setWidth(stage.getWidth() * 0.5f);
        textBoxImage.setHeight(stage.getHeight() * 0.15f);
        textBoxImage.setPosition(stage.getWidth() * 0.14f, stage.getHeight() * 0.85f, Align.left);
        stage.addActor(textBoxImage);
    }

    private void createEraseButton() {
        eraseButton = ButtonFactory.makeButton(
                xlpooLsionClient, "backspace_button.png", "backspace_button.png", stage.getWidth() * 0.7f, stage.getHeight() * 0.65f,
                stage.getWidth() * 0.06f, stage.getHeight() * 0.10f);

        eraseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateInput(-1);
            }
        });
        stage.addActor(eraseButton);
    }

    private void updateInput(int insertedNumber) {
        if(insertedNumber == -1) {
            if(numberInsertIndex > 0) {
                numberInsertIndex--;
                ipNumbers[numberInsertIndex].setDrawable(cleanBackgroundDrawable);
                eraseLastChar();
            }
        } else if(numberInsertIndex < MAX_NUMBERS && insertedNumber < 10) {
            connectIp += insertedNumber;
            ipNumbers[numberInsertIndex].setDrawable(ipNumberDrawables[insertedNumber]);
            numberInsertIndex++;
        }
    }

    private void eraseLastChar(){
        if (connectIp != null && connectIp.length() > 0) {
            connectIp = connectIp.substring(0, connectIp.length() - 1);
        }
    }
    private void createConnectButton() {
        connectButton = ButtonFactory.makeButton(
                xlpooLsionClient, "button_connect__Up.png", "button_connect__down.png", textBoxImage.getX() + stage.getWidth() * 0.7f, textBoxImage.getY() + stage.getHeight() * 0.08f,
                stage.getWidth() * 0.25f, stage.getHeight() * 0.15f);

        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String parsedIP = Connection.parseIP(connectIp);
                System.out.println("Attempting to connect to ip: " + parsedIP);
                if(parsedIP != null) {
                    try {
                        //TODO: Move port number to protocol class
                        NetworkRouter.getInstance().setConnection(new Connection(parsedIP, 9876));
                    } catch (IOException e) {
                        System.out.println("Couldn't connect to server");
                        e.printStackTrace();
                    }
                }
            }
        });
        stage.addActor(connectButton);
    }

    private void createBackButton() {
        backButton = ButtonFactory.makeButton(
                xlpooLsionClient, "button_backUP.png", "button_backDOWN.png", textBoxImage.getX() + stage.getWidth() * 0.74f, stage.getHeight() * 0.15f,
                stage.getWidth() * 0.15f, stage.getHeight() * 0.10f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
            }
        });
        stage.addActor(backButton);
    }
}
