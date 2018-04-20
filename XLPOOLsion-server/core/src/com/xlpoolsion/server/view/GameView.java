package com.xlpoolsion.server.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.GameModel;
import com.xlpoolsion.server.model.entities.PlayerModel;
import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.controller.GameController.GAME_WIDTH;

public class GameView extends ScreenAdapter {
    //Adjust according to the player height in the future
    public static final float PIXEL_TO_METER = 0.08f;

    //The ingame viewport is supposed to show the full game + some space for the HUD/UI
    private static final float VIEWPORT_WIDTH = GAME_WIDTH * 1.1f;
    private static final float VIEWPORT_WIDTH_PX = VIEWPORT_WIDTH / PIXEL_TO_METER;

    private XLPOOLsionServer xlpooLsionServer;
    private Viewport viewport;

    //Replaced by viewfactory?
    private PlayerView playerView;

    public GameView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        loadAssets();

        playerView = new PlayerView(xlpooLsionServer);

        //Creating a viewport with consistent aspect ratio
        viewport = new FitViewport(VIEWPORT_WIDTH_PX, VIEWPORT_WIDTH_PX * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
    }

    private void loadAssets() {
        this.xlpooLsionServer.getAssetManager().load("Bomberman_sprite.png", Texture.class);
        this.xlpooLsionServer.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        GameController.getInstance().update(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        xlpooLsionServer.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        xlpooLsionServer.getBatch().begin();
        drawEntities();
        xlpooLsionServer.getBatch().end();
    }

    private void drawEntities() {
        //TODO: ViewFactory? Sounded nice
        PlayerModel playerModel = GameModel.getInstance().getPlayer();
        playerView.update(playerModel);
        playerView.draw(xlpooLsionServer.getBatch());
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameController.getInstance().movePlayerUp(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().movePlayerRight(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            GameController.getInstance().movePlayerDown(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameController.getInstance().movePlayerLeft(delta);
        } else {
            GameController.getInstance().stopPlayer(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
    }
}
