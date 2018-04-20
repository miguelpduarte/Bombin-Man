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
import com.xlpoolsion.server.model.PlayerModel;

public class GameView extends ScreenAdapter {

    public static final float PIXEL_TO_METER = 0.08f;

    private XLPOOLsionServer xlpooLsionServer;
    private Viewport viewport;

    //To be moved elsewhere
    private PlayerView playerView;
    private Texture test_img;

    public GameView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        loadAssets();

        playerView = new PlayerView(xlpooLsionServer);

        viewport = new FitViewport(400, 240);
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
