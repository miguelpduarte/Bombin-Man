package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.xlpoolsion.server.XLPOOLsionServer;

public class WinScreen extends BaseScreen {
    public WinScreen(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer, Type.Lobby);
    }

    @Override
    protected void loadAssets() {
        xlpooLsionServer.getAssetManager().load("BackgroundLobby.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();
    }

    @Override
    protected void addUIElements() {
        createBackground();
    }

    private void createBackground() {
        Image background = new Image((Texture) xlpooLsionServer.getAssetManager().get("BackgroundLobby.jpg"));
        background.setSize(stage.getWidth(), stage.getHeight());
        background.setZIndex(0);
        stage.addActor(background);
    }

    //Função de click do botão de back deve mudar o estado para lobby screen de novo (state reset) -> Para ter a certeza tentar kickar todos os players?
    //Já deverão estar kickados by then, mesmo a win message fecha a ligação
}
