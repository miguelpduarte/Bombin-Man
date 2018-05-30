package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.networking.MultithreadedServer;
import com.xlpoolsion.server.networking.NetworkInfo;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.ButtonFactory;

public class LobbyScreen extends BaseScreen {
    private BitmapFont main_size50;
    private BitmapFont bold_size50;
    private Label ipLabel;
    private Label ipLabelText;
    private Drawable checkDrawable;
    private Drawable crossDrawable;
    private Image[] playerStatusImages;
    private Label[] playerStatusLabels;

    public LobbyScreen(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer, Type.Lobby);

        NetworkRouter.getInstance().setServer(new MultithreadedServer());
    }

    @Override
    protected void loadAssets() {
        loadFonts();
        xlpooLsionServer.getAssetManager().load("badlogic.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().load("explosion.png", Texture.class);
        xlpooLsionServer.getAssetManager().load("Wall.png", Texture.class);
        xlpooLsionServer.getAssetManager().load("SolidWall.png", Texture.class);
        xlpooLsionServer.getAssetManager().load("BackgroundLobby.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().load("green_check_512.png", Texture.class);
        xlpooLsionServer.getAssetManager().load("red_cross_512.png", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();
        createFonts();
        createPlayerStatusDrawables();
    }

    private void createPlayerStatusDrawables() {
        checkDrawable = new TextureRegionDrawable(new TextureRegion(xlpooLsionServer.getAssetManager().get("green_check_512.png", Texture.class)));
        crossDrawable = new TextureRegionDrawable(new TextureRegion(xlpooLsionServer.getAssetManager().get("red_cross_512.png", Texture.class)));
    }

    private void createFonts() {
        main_size50 = xlpooLsionServer.getAssetManager().get("main_size50.ttf", BitmapFont.class);
        bold_size50 = xlpooLsionServer.getAssetManager().get("bold_size50.ttf", BitmapFont.class);
    }

    private void loadFonts() {
        // load to fonts via the generator (implicitely done by the FreetypeFontLoader).
        // Note: you MUST specify a FreetypeFontGenerator defining the ttf font file name and the size
        // of the font to be generated. The names of the fonts are arbitrary and are not pointing
        // to a file on disk (but must end with the font's file format '.ttf')!
        FreetypeFontLoader.FreeTypeFontLoaderParameter candl_regular_50px = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        candl_regular_50px.fontFileName = "fonts/Champagne & Limousines.ttf";
        candl_regular_50px.fontParameters.size = 50;
        xlpooLsionServer.getAssetManager().load("main_size50.ttf", BitmapFont.class, candl_regular_50px);

        FreetypeFontLoader.FreeTypeFontLoaderParameter candl_bold_50px = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        candl_bold_50px.fontFileName = "fonts/Champagne & Limousines Bold.ttf";
        candl_bold_50px.fontParameters.size = 50;
        xlpooLsionServer.getAssetManager().load("bold_size50.ttf", BitmapFont.class, candl_bold_50px);

        /*FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size2Params.fontFileName = "data/arial.ttf";
        size2Params.fontParameters.size = 20;
        manager.load("size20.ttf", BitmapFont.class, size2Params);*/
    }

    @Override
    protected void addUIElements() {
        createBackground();
        createPlayButton();
        createIPLabel();
        createExitButton();
        createPlayerStatusLabels();
        initPlayerStatusImages();
    }

    private static final float PLAYER_LABELS_STARTING_WIDTH = Gdx.graphics.getWidth() * 0.24f;
    private static final float PLAYER_LABELS_STARTING_HEIGHT = Gdx.graphics.getHeight() * 0.22f;
    private static final float PLAYER_LABELS_HEIGHT_PADDING = Gdx.graphics.getHeight() * 0.09f;
    private static final float PLAYER_STATUS_IMAGE_WIDTH = 64 * Gdx.graphics.getWidth() / 1920;
    private static final float PLAYER_STATUS_IMAGE_HEIGHT = 64 * Gdx.graphics.getHeight() / 1080;
    private static final float PLAYER_STATUS_LABEL_TO_IMAGE_SPACING = 16;

    private void createPlayerStatusLabels() {
        playerStatusLabels = new Label[GameController.MAX_PLAYERS];
        Label.LabelStyle lb_style = new Label.LabelStyle();
        lb_style.font = bold_size50;
        lb_style.fontColor = Color.WHITE;

        for(int i = 0; i < playerStatusLabels.length; ++i) {
            playerStatusLabels[i] = new Label("Player " + (i+1) + ": ", lb_style);
            playerStatusLabels[i].setPosition(PLAYER_LABELS_STARTING_WIDTH, PLAYER_LABELS_STARTING_HEIGHT + (playerStatusLabels.length - i) * PLAYER_LABELS_HEIGHT_PADDING, Align.topLeft);
            stage.addActor(playerStatusLabels[i]);
        }
    }

    private void initPlayerStatusImages() {
        playerStatusImages = new Image[GameController.MAX_PLAYERS];

        for (int i = 0; i < playerStatusImages.length; ++i) {
            playerStatusImages[i] = new Image();
            playerStatusImages[i].setPosition(playerStatusLabels[i].getX() + playerStatusLabels[i].getWidth() + PLAYER_STATUS_LABEL_TO_IMAGE_SPACING, playerStatusLabels[i].getY(), Align.topLeft);
            playerStatusImages[i].setDrawable(crossDrawable);
            playerStatusImages[i].setSize(PLAYER_STATUS_IMAGE_WIDTH, PLAYER_STATUS_IMAGE_HEIGHT);
            stage.addActor(playerStatusImages[i]);
        }
    }

    private void createBackground() {
        Image background = new Image((Texture) xlpooLsionServer.getAssetManager().get("BackgroundLobby.jpg"));
        background.setSize(stage.getWidth(), stage.getHeight());
        background.setZIndex(0);
        stage.addActor(background);
    }

    private void createIPLabel() {
        createIPLabelLabel();
        createIPLabelText();
    }

    private void createIPLabelLabel() {
        Label.LabelStyle lb_style = new Label.LabelStyle();
        lb_style.font = bold_size50;
        lb_style.fontColor = Color.WHITE;
        ipLabel = new Label("Server IP: ", lb_style);
        ipLabel.setPosition(stage.getWidth() * 0.2f, stage.getHeight() * 0.65f, Align.left);
        stage.addActor(ipLabel);
    }

    private void createIPLabelText() {
        Label.LabelStyle lb_style = new Label.LabelStyle();
        lb_style.font = main_size50;
        lb_style.fontColor = Color.WHITE;
        ipLabelText = new Label(NetworkInfo.getInstance().getServerIP(), lb_style);
        ipLabelText.setPosition(stage.getWidth() * 0.2f + ipLabel.getWidth(), stage.getHeight() * 0.65f, Align.left);
        stage.addActor(ipLabelText);
    }

    private void createPlayButton() {
        Button playButton = ButtonFactory.makeButton(
                xlpooLsionServer, "Wall.png", "SolidWall.png",
                stage.getWidth() * 0.7f, stage.getHeight() * 0.4f, stage.getWidth() * 0.1f, stage.getHeight() * 0.1f);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().startGame(0);
            }
        });
        stage.addActor(playButton);
    }


    private void createExitButton() {
        Button exitButton = ButtonFactory.makeButton(
                xlpooLsionServer, "explosion.png", "badlogic.jpg",
                stage.getWidth() * 0.7f, stage.getHeight() * 0.2f, stage.getWidth() * 0.1f, stage.getHeight() * 0.1f);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().closeGame();
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //Switching screen in case game was entered
        switch (GameController.getInstance().getCurrentState()) {
            case PLAYING:
                xlpooLsionServer.setScreen(new GameScreen(xlpooLsionServer));
                break;
        }

        updatePlayerStatusImages(NetworkRouter.getInstance().getServer().getConnectedClients());
    }

    private void updatePlayerStatusImages(boolean[] connectedClients) {
        for(int i = 0; i < connectedClients.length; ++i) {
            if(connectedClients[i]) {
                playerStatusImages[i].setDrawable(checkDrawable);
            } else {
                playerStatusImages[i].setDrawable(crossDrawable);
            }
        }
    }
}
