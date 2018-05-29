package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.xlpoolsion.client.XLPOOLsionClient;

public abstract class StageScreen extends ScreenAdapter {
    protected XLPOOLsionClient xlpooLsionClient;

    protected Stage stage;

    public StageScreen(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        loadAssets();
        createGUI();
    }

    protected abstract void loadAssets();

    protected abstract void createGUI();

    @Override
    public void render(float delta) {
        clearScreen();
        stage.act(delta);
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setPortrait() {
        stage.getCamera().rotate(270, 0, 0, 1);
    }
}
