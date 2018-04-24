package com.xlpoolsion.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xlpoolsion.client.XLPOOLsionClient;

public class StageView extends ScreenAdapter {
    protected XLPOOLsionClient xlpooLsionClient;

    protected Stage stage;

    public StageView(XLPOOLsionClient xlpooLsionClient) {
        this.xlpooLsionClient = xlpooLsionClient;
        stage = new Stage(new ExtendViewport(640, 360));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        stage.act(delta);
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
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
}
