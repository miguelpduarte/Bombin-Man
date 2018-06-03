package com.xlpoolsion.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.xlpoolsion.client.XLPOOLsionClient;

/**
 * Abstract class used as base for the other screens
 */
public abstract class StageScreen extends ScreenAdapter {
    protected XLPOOLsionClient xlpooLsionClient;

    protected Stage stage;

    /**
     * Creates a stage screen
     * @param xlpooLsionClient The game this screen belongs to
     */
    public StageScreen(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        loadAssets();
        createGUI();
    }

    protected abstract void loadAssets();

    protected abstract void createGUI();

    /**
     * Updates the stage and draws it
     * @param delta the time passed
     */
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

    /**
     * Resizes the stage
     * @param width width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    /**
     * Disposes of the screen
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setPortrait() {
        stage.getCamera().rotate(270, 0, 0, 1);
    }
}
