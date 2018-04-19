package com.xlpoolsion.server.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.PlayerModel;

public class GameView extends ScreenAdapter {

    private XLPOOLsionServer xlpooLsionServer;
    private Viewport viewport;
    private Camera camera;

    //To be moved elsewhere
    private PlayerView playerView;
    private PlayerModel playerModel;
    private Texture test_img;

    public GameView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        loadAssets();

        test_img = new Texture(Gdx.files.internal("badlogic.jpg"));

        //TODO: Move?
        playerModel = new PlayerModel();
        playerView = new PlayerView(xlpooLsionServer);

        camera = new OrthographicCamera();

        viewport = new FitViewport(800, 480, camera);
    }

    private void loadAssets() {
        this.xlpooLsionServer.getAssetManager().load("Bomberman_sprite.png", Texture.class);
        this.xlpooLsionServer.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        handleInputs();

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        xlpooLsionServer.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        xlpooLsionServer.getBatch().begin();
        playerView.draw(delta, xlpooLsionServer.getBatch(), playerModel);
        xlpooLsionServer.getBatch().end();
    }

    private void handleInputs() {
        //TODO: Pass through controller instead of direct access to model

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerModel.setCurrentOrientation(PlayerModel.Orientation.UP);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerModel.setCurrentOrientation(PlayerModel.Orientation.RIGHT);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerModel.setCurrentOrientation(PlayerModel.Orientation.DOWN);
        } else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerModel.setCurrentOrientation(PlayerModel.Orientation.LEFT);
        }

        if(Gdx.input.isTouched()) {
            playerModel.setX(Gdx.input.getX());
            playerModel.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
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
