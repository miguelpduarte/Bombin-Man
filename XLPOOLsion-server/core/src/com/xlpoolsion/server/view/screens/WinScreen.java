package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.PlayerModel;
import com.xlpoolsion.server.view.TextureManager;

import java.util.ArrayList;
import java.util.Collections;

public class WinScreen extends BaseScreen {
    private Animation<TextureRegion> winnerAnimation;
    private float stateTime = 0;
    private Image winnerImage;
    private Image winnerImage2;
    private ArrayList<PlayerModel> playersLastInfo;
    private BitmapFont main_size30;
    private Label winnerLabelText;

    public static final float FIRST_ACHIEVEMENT_X = Gdx.graphics.getWidth()/2 - 200;
    public static final float FIRST_ACHIEVEMENT_Y = Gdx.graphics.getHeight()/2 + 200;
    public static final float Y_GAP = 100;

    public WinScreen(XLPOOLsionServer xlpooLsionServer, ArrayList<PlayerModel> playersLastInfo) {
        super(xlpooLsionServer, Type.Win_Screen);
        this.playersLastInfo = playersLastInfo;
        Collections.reverse(this.playersLastInfo);
        setWinnerAnimation();
        createAcheievmentsLabel();
    }


    @Override
    protected void loadAssets() {
        loadFonts();
        xlpooLsionServer.getAssetManager().load("WinningScreenBackground.png", Texture.class);
        xlpooLsionServer.getAssetManager().finishLoading();
        //Get based on the winner player when that is implemented later -> see ViewFactory switch case for player, do something similar
        winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.WHITE, TextureManager.PlayerAnimType.VICTORY);
        createFonts();
    }

    private void loadFonts() {
        FreetypeFontLoader.FreeTypeFontLoaderParameter candl_regular_10px = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        candl_regular_10px.fontFileName = "fonts/Champagne & Limousines.ttf";
        candl_regular_10px.fontParameters.size = 30;
        xlpooLsionServer.getAssetManager().load("main_size30.ttf", BitmapFont.class, candl_regular_10px);

    }

    private void createFonts() {
        main_size30 = xlpooLsionServer.getAssetManager().get("main_size30.ttf", BitmapFont.class);
    }
    @Override
    protected void addUIElements() {
        createBackground();
        createWinnerImage();
    }

    private void createAcheievmentsLabel() {
        createWinnerLabelText();
    }
    private void createWinnerLabelText() {
        Label.LabelStyle lb_style = new Label.LabelStyle();
        lb_style.font = main_size30;
        lb_style.fontColor = Color.WHITE;
        winnerLabelText = new Label(getWinnerText(), lb_style);
        winnerLabelText.setPosition(stage.getWidth() * 0.31f, stage.getHeight() * 0.66f, Align.left);
        stage.addActor(winnerLabelText);
    }
    private String getWinnerText(){
        return "Player " + playersLastInfo.get(0).getId() + " is the Winner Congratulations!";
    }


    private void setWinnerAnimation() {
        switch (playersLastInfo.get(0).getId()) {
            case 1:
                winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.VICTORY);
                break;
            case 2:
                winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.VICTORY);
                break;
            case 3:
                winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.RED, TextureManager.PlayerAnimType.VICTORY);
                break;
            default:
                winnerAnimation = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.WHITE, TextureManager.PlayerAnimType.VICTORY);
                break;

        }
    }
    private void createWinnerImage() {
        winnerImage = new Image(winnerAnimation.getKeyFrame(0f, false));
        //Scaling up player animation, should be done relative to screen but oh well
        winnerImage.setWidth(winnerAnimation.getKeyFrame(0f, false).getRegionWidth() * 14);
        winnerImage.setHeight(winnerAnimation.getKeyFrame(0f, false).getRegionHeight() * 14);
        winnerImage.setPosition(stage.getWidth() * 0.1f, stage.getHeight() * 0.5f, Align.center);

        winnerImage2 = new Image(winnerAnimation.getKeyFrame(0f, false));
        winnerImage2.setWidth(winnerAnimation.getKeyFrame(0f, false).getRegionWidth() * 14);
        winnerImage2.setHeight(winnerAnimation.getKeyFrame(0f, false).getRegionHeight() * 14);
        winnerImage2.setPosition(stage.getWidth() * 0.9f, stage.getHeight() * 0.5f, Align.center);

        stage.addActor(winnerImage);
        stage.addActor(winnerImage2);
    }

    private void createBackground() {
        Image background = new Image((Texture) xlpooLsionServer.getAssetManager().get("WinningScreenBackground.png"));
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
        winnerImage2.setDrawable(new TextureRegionDrawable(winnerAnimation.getKeyFrame(stateTime,true)));
    }
//Função de click do botão de back deve mudar o estado para lobby screen de novo (state reset) -> Para ter a certeza tentar kickar todos os players?
    //Já deverão estar kickados by then, mesmo a win message fecha a ligação
}
