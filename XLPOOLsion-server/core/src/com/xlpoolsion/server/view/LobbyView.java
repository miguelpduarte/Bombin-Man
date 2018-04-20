package com.xlpoolsion.server.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.xlpoolsion.common.Message;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.networking.MultithreadedServer;
import com.xlpoolsion.server.networking.NetworkRouter;

import java.io.IOException;

public class LobbyView extends ScreenAdapter {
    private final XLPOOLsionServer xlpooLsionServer;
    private final Texture img;

    public LobbyView(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        xlpooLsionServer.getAssetManager().load("badlogic.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();

        this.img = xlpooLsionServer.getAssetManager().get("badlogic.jpg");

        try {
            NetworkRouter.getInstance().setServer(new MultithreadedServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.6f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        xlpooLsionServer.getBatch().begin();
        xlpooLsionServer.getBatch().draw(img, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        xlpooLsionServer.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            NetworkRouter.getInstance().sendToAll(new Message(Message.MessageType.TEST_MESSAGE));
        }
    }

    @Override
    public void dispose() {
    }
}
