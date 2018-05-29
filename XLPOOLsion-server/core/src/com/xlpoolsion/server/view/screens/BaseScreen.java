package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.xlpoolsion.server.XLPOOLsionServer;

public abstract class BaseScreen extends ScreenAdapter {
    public enum Type {Lobby, Clients_Disconnected};

    private Type type;
    protected Stage stage;
    private XLPOOLsionServer xlpooLsionServer;

    public BaseScreen(XLPOOLsionServer xlpooLsionServer, Type screenType) {
        this.xlpooLsionServer = xlpooLsionServer;
        this.type = screenType;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        loadAssets(xlpooLsionServer);
        addUIElements(xlpooLsionServer);
    }

    protected abstract void loadAssets(XLPOOLsionServer xlpooLsionServer);

    protected abstract void addUIElements(XLPOOLsionServer xlpooLsionServer);

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0.6f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        stage.act(delta);
        stage.draw();
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
