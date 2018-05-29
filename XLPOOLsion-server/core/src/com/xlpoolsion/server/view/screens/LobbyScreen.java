package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.networking.MultithreadedServer;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.ButtonFactory;

import java.io.IOException;

public class LobbyScreen extends BaseScreen {
    private final XLPOOLsionServer xlpooLsionServer;

    public LobbyScreen(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer, Type.Lobby);
        this.xlpooLsionServer = xlpooLsionServer;

        try {
            NetworkRouter.getInstance().setServer(new MultithreadedServer());
        } catch (IOException e) {
            System.out.println("OMG WHAT A SERVER CREATION EXCEPTION");
            e.printStackTrace();
        }
    }

    protected void loadAssets(XLPOOLsionServer xlpooLsionServer) {
        //xlpooLsionServer.getAssetManager().load("badlogic.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().load("Wall.png", Texture.class);
        xlpooLsionServer.getAssetManager().load("SolidWall.png", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();
    }

    @Override
    protected void addUIElements(XLPOOLsionServer xlpooLsionServer) {
        createPlayButton(xlpooLsionServer);
    }

    private void createPlayButton(XLPOOLsionServer xlpooLsionServer) {
        Button playButton = ButtonFactory.makeButton(
                xlpooLsionServer, "Wall.png", "SolidWall.png",
                stage.getWidth() * 0.4f, stage.getHeight() * 0.3f, stage.getWidth() * 0.3f, stage.getHeight() * 0.1f);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().startGame(0);
            }
        });
        stage.addActor(playButton);
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
    }
}
