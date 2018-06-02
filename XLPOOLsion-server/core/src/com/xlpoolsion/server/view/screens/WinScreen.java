package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.view.TextureManager;

public class WinScreen extends BaseScreen {
    private Animation<TextureRegion> winnerAnimation;
    private float stateTime = 0;
    private Image winnerImage;

    public WinScreen(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer, Type.Win_Screen);
    }

    @Override
    protected void loadAssets() {
        xlpooLsionServer.getAssetManager().load("BackgroundLobby.jpg", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();
        //Get based on the winner player when that is implemented later -> see ViewFactory switch case for player, do something similar
        winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.WHITE, TextureManager.PlayerAnimType.VICTORY);
    }

    @Override
    protected void addUIElements() {
        createBackground();
        createWinnerImage();
    }

    private void createWinnerImage() {
        winnerImage = new Image(winnerAnimation.getKeyFrame(0f, false));
        //Scaling up player animation, should be done relative to screen but oh well
        winnerImage.setWidth(winnerAnimation.getKeyFrame(0f, false).getRegionWidth() * 14);
        winnerImage.setHeight(winnerAnimation.getKeyFrame(0f, false).getRegionHeight() * 14);
        winnerImage.setPosition(stage.getWidth() * 0.5f, stage.getHeight() * 0.4f, Align.center);
        stage.addActor(winnerImage);
    }

    private void createBackground() {
        Image background = new Image((Texture) xlpooLsionServer.getAssetManager().get("BackgroundLobby.jpg"));
        background.setSize(stage.getWidth(), stage.getHeight());
        background.setZIndex(0);
        stage.addActor(background);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += delta;
        //I just wish I didn't have to do this
        winnerImage.setDrawable(new TextureRegionDrawable(winnerAnimation.getKeyFrame(stateTime,true)));
    }
//Função de click do botão de back deve mudar o estado para lobby screen de novo (state reset) -> Para ter a certeza tentar kickar todos os players?
    //Já deverão estar kickados by then, mesmo a win message fecha a ligação
}
