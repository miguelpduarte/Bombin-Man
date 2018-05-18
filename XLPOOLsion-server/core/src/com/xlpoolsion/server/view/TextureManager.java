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
        this.assetManager.load("Bomb_sprite_transparent.png", Texture.class);
        this.assetManager.load("explosion.png", Texture.class);
        this.assetManager.load("Wall.png", Texture.class);
        this.assetManager.load("BreakableBrickWall.png", Texture.class);
        this.assetManager.finishLoading();
    }

    public Animation<TextureRegion> getBombAnimation() {
        return bombAnimation;
    }
}
