package com.xlpoolsion.server.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.view.entities.BombView;

public class TextureManager {
    private AssetManager assetManager;
    private Animation<TextureRegion> bombAnimation;
    private Texture fullBombSpritesTexture;
    private TextureRegion[][] bombSpritesSplit;

    public TextureManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        loadAssets();
        loadTextures();
        buildAnimations();
    }

    private void loadTextures() {
        loadBombSprites();
    }

    private void loadBombSprites() {
        fullBombSpritesTexture = assetManager.get("Bomb_sprite_transparent.png");
        bombSpritesSplit = TextureRegion.split(fullBombSpritesTexture, 16, 16);
    }

    private void buildAnimations() {
        createBombAnimation();
    }

    private void createBombAnimation() {
        TextureRegion[] bomb_anim_frames =  new TextureRegion[4];
        System.arraycopy(bombSpritesSplit[0], 0, bomb_anim_frames, 0, 4);

        bombAnimation = new Animation<TextureRegion>(BombView.FRAME_TIME, bomb_anim_frames);
    }

    private void loadAssets() {
        this.assetManager.load("Bomberman_sprite.png", Texture.class);
        this.assetManager.load("Bomberman_sprite_Blue.png", Texture.class);
        this.assetManager.load("Bomberman_sprite_Black.png", Texture.class);
        this.assetManager.load("Bomberman_sprite_Red.png", Texture.class);
        this.assetManager.load("Bomb_sprite_transparent.png", Texture.class);
        this.assetManager.load("explosion.png", Texture.class);
        this.assetManager.load("SolidBrick.png", Texture.class);
        this.assetManager.load("BreakableBrick2.png", Texture.class);
        this.assetManager.load("PwrRadiusUp.png", Texture.class);
        this.assetManager.load("PwrRadiusDown.png", Texture.class);
        this.assetManager.load("PwrSpeedUp.png", Texture.class);
        this.assetManager.load("PwrSpeedDown.png", Texture.class);
        this.assetManager.load("PwrBombUp.png", Texture.class);
        this.assetManager.load("PwrBombDown.png", Texture.class);
        this.assetManager.load("UI_Box00.png", Texture.class);
        this.assetManager.load("UI_Box01.png", Texture.class);
        this.assetManager.load("UI_Box02.png", Texture.class);
        this.assetManager.load("UI_Box03.png", Texture.class);
        this.assetManager.load("UI_Box00DC.png", Texture.class);
        this.assetManager.load("UI_Box01DC.png", Texture.class);
        this.assetManager.load("UI_Box02DC.png", Texture.class);
        this.assetManager.load("UI_Box03DC.png", Texture.class);
        this.assetManager.load("0Text.png", Texture.class);
        this.assetManager.load("1Text.png", Texture.class);
        this.assetManager.load("2Text.png", Texture.class);
        this.assetManager.load("3Text.png", Texture.class);
        this.assetManager.load("4Text.png", Texture.class);
        this.assetManager.load("5Text.png", Texture.class);
        this.assetManager.load("6Text.png", Texture.class);
        this.assetManager.load("7Text.png", Texture.class);
        this.assetManager.load("8Text.png", Texture.class);
        this.assetManager.load("9Text.png", Texture.class);
        //Temporary for stun power
        this.assetManager.load("red_cross_512.png", Texture.class);
        this.assetManager.finishLoading();
    }

    public Animation<TextureRegion> getBombAnimation() {
        return bombAnimation;
    }
}
