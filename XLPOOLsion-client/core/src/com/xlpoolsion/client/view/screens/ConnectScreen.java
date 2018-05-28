package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.networking.Connection;
import com.xlpoolsion.client.networking.NetworkRouter;
import com.xlpoolsion.client.view.ButtonFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectScreen extends StageScreen {
    private Button button1;
    private Button button2;
    private Button button3;
    private Image textBoxImage;
    private ArrayList<Image> ipNumbers = new ArrayList<Image>();
    private int currImg = 0;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private String connectIp = "";

    public ConnectScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);

        loadAssets();
        createElements();
    }

    private void loadAssets() {
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
        xlpooLsionClient.getAssetManager().finishLoading();
    }


    private void createElements() {
        createButton1();
        createButton2();
        createButton3();
        createButton4();
        createButton5();
        createButton6();
        createButton7();
        createButton8();
        createButton9();
        createImageTextBox();
        initializeIpNumbers();

    }

    private void initializeIpNumbers() {
        Image Slot;

        float imageWidth = stage.getHeight() * 0.04f;
        float imageHeight = stage.getHeight() * 0.1f;
        float imageStartX = textBoxImage.getX() + imageWidth;
        float imageStartY = textBoxImage.getY() + stage.getWidth() * 0.02f;

        for(int i = 0; i < 12;i++){
            Slot = new Image();
            Slot.setPosition(imageStartX + (imageWidth * i),imageStartY,Align.center);
            Slot.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("CleanBackground.png"))));
            Slot.setSize(imageWidth, imageHeight);
            ipNumbers.add(Slot);
            stage.addActor(ipNumbers.get(i));
        }
    }

    private void createImageTextBox() {
        textBoxImage = new Image(new Texture("TextBoxImage.png"));
        textBoxImage.setWidth(stage.getWidth() * 0.5f);
        textBoxImage.setHeight(stage.getHeight() * 0.15f);
        textBoxImage.setPosition(stage.getWidth() * 0.25f, stage.getHeight() * 0.85f, Align.left);
        stage.addActor(textBoxImage);
    }

    /* private void createConnectButton() {
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
 */
    private void createButton1() {
        button1 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-1.png", "Bomb-1.png", stage.getWidth() * 0.40f, stage.getHeight() * 0.65f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "1";
                System.out.println(connectIp);
                if(currImg < 12){
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("1Text.png"))));
                    currImg++;
                }

            }
        });
        stage.addActor(button1);
    }
    private void createButton2() {
        button2 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-2.png", "Bomb-2.png", stage.getWidth() * 0.50f, stage.getHeight() * 0.65f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "2";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("2Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button2);
    }
    private void createButton3() {
        button3 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-3.png", "Bomb-3.png", stage.getWidth() * 0.60f, stage.getHeight() * 0.65f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "3";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("3Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button3);
    }
    private void createButton4() {
        button4 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-4.png", "Bomb-4.png", stage.getWidth() * 0.40f, stage.getHeight() * 0.50f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "4";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("4Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button4);
    }
    private void createButton5() {
        button5 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-5.png", "Bomb-5.png", stage.getWidth() * 0.50f, stage.getHeight() * 0.50f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "5";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("5Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button5);
    }
    private void createButton6() {
        button6 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-6.png", "Bomb-6.png", stage.getWidth() * 0.60f, stage.getHeight() * 0.50f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "6";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("6Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button6);
    }
    private void createButton7() {
        button7 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-7.png", "Bomb-7.png", stage.getWidth() * 0.40f, stage.getHeight() * 0.35f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button7.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "7";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("7Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button7);
    }
    private void createButton8() {
        button8 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-8.png", "Bomb-8.png", stage.getWidth() * 0.50f, stage.getHeight() * 0.35f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button8.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "8";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("8Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button8);
    }
    private void createButton9() {
        button9 = ButtonFactory.makeButton(
                xlpooLsionClient, "Bomb-9.png", "Bomb-9.png", stage.getWidth() * 0.60f, stage.getHeight() * 0.35f,
                stage.getHeight() * 0.15f, stage.getHeight() * 0.15f);

        button9.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectIp += "9";
                System.out.println(connectIp);
                if(currImg < 12) {
                    ipNumbers.get(currImg).setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("9Text.png"))));
                    currImg++;
                }
            }
        });
        stage.addActor(button9);
    }
}
