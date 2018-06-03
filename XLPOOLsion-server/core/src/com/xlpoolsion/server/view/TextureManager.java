package com.xlpoolsion.server.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.model.entities.ExplosionModel;
import com.xlpoolsion.server.view.entities.BombView;
import com.xlpoolsion.server.view.entities.ExplosionView;
import com.xlpoolsion.server.view.entities.PlayerView;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class in charge of loading all assets and creating animations
 */
public class TextureManager {
    private AssetManager assetManager;
    private Texture fullBombSpritesTexture;
    private TextureRegion[][] bombSpritesSplit;
    private Animation<TextureRegion> bombAnimation;
    private TextureRegion[][] explosionsSpritesSplit;

    /**
     * Enum used to define the different types of animations
     */
    public enum PlayerAnimType {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        DEATH,
        STUN,
        VICTORY
    }

    /**
     * Enum used to define the different type of player colors
     */
    public enum PlayerColor {
        WHITE,
        BLUE,
        BLACK,
        RED
    }

    private Texture fullExplosionSpritesTexture;
    private EnumMap<ExplosionModel.Direction, Animation<TextureRegion>> explosionAnimations = new EnumMap<ExplosionModel.Direction, Animation<TextureRegion>>(ExplosionModel.Direction.class);

    private Map<PlayerColor, Texture> fullPlayerSpritesTexture = new EnumMap<PlayerColor, Texture>(PlayerColor.class);
    private Map<PlayerColor, TextureRegion[][]> fullPlayerSpritesSplit = new EnumMap<PlayerColor, TextureRegion[][]>(PlayerColor.class);

    //Used only for putting in the fully fledged map below
    private EnumMap<PlayerAnimType, Animation<TextureRegion>> whitePlayerAnimations = new EnumMap<PlayerAnimType, Animation<TextureRegion>>(PlayerAnimType.class);
    private EnumMap<PlayerAnimType, Animation<TextureRegion>> bluePlayerAnimations = new EnumMap<PlayerAnimType, Animation<TextureRegion>>(PlayerAnimType.class);
    private EnumMap<PlayerAnimType, Animation<TextureRegion>> blackPlayerAnimations = new EnumMap<PlayerAnimType, Animation<TextureRegion>>(PlayerAnimType.class);
    private EnumMap<PlayerAnimType, Animation<TextureRegion>> redPlayerAnimations = new EnumMap<PlayerAnimType, Animation<TextureRegion>>(PlayerAnimType.class);
    //Actual fully fledged map
    private EnumMap<PlayerColor, EnumMap<PlayerAnimType, Animation<TextureRegion> >> playerAnimations = new EnumMap<PlayerColor, EnumMap<PlayerAnimType, Animation<TextureRegion>>>(PlayerColor.class);

    /**
     * Creates the manager used to manage all textures and animations
     * @param assetManager The asset manager used
     */
    public TextureManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        allocateMap();
        loadAssets();
        loadTextures();
        buildAnimations();
    }

    /**
     * Creates a map with the different types of animations
     */
    private void allocateMap() {
        playerAnimations.put(PlayerColor.WHITE, whitePlayerAnimations);
        playerAnimations.put(PlayerColor.BLUE, bluePlayerAnimations);
        playerAnimations.put(PlayerColor.BLACK, blackPlayerAnimations);
        playerAnimations.put(PlayerColor.RED, redPlayerAnimations);
    }

    private void loadTextures() {
        loadBombSprites();
        loadPlayerSprites();
        loadExplosionSprites();
    }

    private void loadExplosionSprites() {
        fullExplosionSpritesTexture = assetManager.get("ExplosionAnimation.png");
        explosionsSpritesSplit = TextureRegion.split(fullExplosionSpritesTexture, 16, 16);
    }

    private void loadPlayerSprites() {
        loadWhitePlayerSprites();
        loadBluePlayerSprites();
        loadBlackPlayerSprites();
        loadRedPlayerSprites();
    }

    private void loadWhitePlayerSprites() {
        fullPlayerSpritesTexture.put(PlayerColor.WHITE, assetManager.get("Bomberman_sprite.png", Texture.class));
        fullPlayerSpritesSplit.put(PlayerColor.WHITE, TextureRegion.split(fullPlayerSpritesTexture.get(PlayerColor.WHITE), 16, 32));
    }

    private void loadBluePlayerSprites() {
        fullPlayerSpritesTexture.put(PlayerColor.BLUE, assetManager.get("Bomberman_sprite_Blue.png", Texture.class));
        fullPlayerSpritesSplit.put(PlayerColor.BLUE, TextureRegion.split(fullPlayerSpritesTexture.get(PlayerColor.BLUE), 16, 32));
    }

    private void loadBlackPlayerSprites() {
        fullPlayerSpritesTexture.put(PlayerColor.BLACK, assetManager.get("Bomberman_sprite_Black.png", Texture.class));
        fullPlayerSpritesSplit.put(PlayerColor.BLACK, TextureRegion.split(fullPlayerSpritesTexture.get(PlayerColor.BLACK), 16, 32));
    }

    private void loadRedPlayerSprites() {
        fullPlayerSpritesTexture.put(PlayerColor.RED, assetManager.get("Bomberman_sprite_Red.png", Texture.class));
        fullPlayerSpritesSplit.put(PlayerColor.RED, TextureRegion.split(fullPlayerSpritesTexture.get(PlayerColor.RED), 16, 32));
    }

    private void loadBombSprites() {
        fullBombSpritesTexture = assetManager.get("Bomb_sprite_transparent.png");
        bombSpritesSplit = TextureRegion.split(fullBombSpritesTexture, 16, 16);
    }

    private void buildAnimations() {
        createBombAnimation();
        createColoredPlayerAnimations();
        createExplosionAnimations();
    }

    private void createExplosionAnimations() {
        createCenterExplosionAnimation();
        createVerticalExplosionAnimation();
        createHorizontalExplosionAnimation();
    }

    private void createCenterExplosionAnimation() {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(explosionsSpritesSplit[2], 0, frames, 0, 5);
        explosionAnimations.put(ExplosionModel.Direction.Center, new Animation<TextureRegion>(ExplosionView.FRAME_TIME, frames));
    }

    private void createVerticalExplosionAnimation() {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(explosionsSpritesSplit[1], 0, frames, 0, 5);
        explosionAnimations.put(ExplosionModel.Direction.Vertical, new Animation<TextureRegion>(ExplosionView.FRAME_TIME, frames));
    }

    private void createHorizontalExplosionAnimation() {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(explosionsSpritesSplit[0], 0, frames, 0, 5);
        explosionAnimations.put(ExplosionModel.Direction.Horizontal, new Animation<TextureRegion>(ExplosionView.FRAME_TIME, frames));
    }

    private void createColoredPlayerAnimations() {
        createPlayerAnimations(PlayerColor.WHITE);
        createPlayerAnimations(PlayerColor.BLUE);
        createPlayerAnimations(PlayerColor.BLACK);
        createPlayerAnimations(PlayerColor.RED);
    }

    private void createPlayerAnimations(PlayerColor color) {
        createUpAnimation(color);
        createRightAnimation(color);
        createDownAnimation(color);
        createLeftAnimation(color);
        createDeathAnimation(color);
        createStunAnimation(color);
        createVictoryAnimation(color);
    }


    private void createUpAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[0], 0, frames, 0, 3);
        playerAnimations.get(color).put(PlayerAnimType.UP, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createRightAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[1], 0, frames, 0, 3);
        playerAnimations.get(color).put(PlayerAnimType.RIGHT, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createDownAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[2], 0, frames, 0, 3);
        playerAnimations.get(color).put(PlayerAnimType.DOWN, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createLeftAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[3], 0, frames, 0, 3);
        playerAnimations.get(color).put(PlayerAnimType.LEFT, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createDeathAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[10];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[24], 0, frames, 0, 10);
        playerAnimations.get(color).put(PlayerAnimType.DEATH, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createStunAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[11], 0, frames, 0, 5);
        playerAnimations.get(color).put(PlayerAnimType.STUN, new Animation<TextureRegion>(PlayerView.FRAME_TIME, frames));
    }

    private void createVictoryAnimation(PlayerColor color) {
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(fullPlayerSpritesSplit.get(color)[27], 0, frames, 0, 3);
        playerAnimations.get(color).put(PlayerAnimType.VICTORY, new Animation<TextureRegion>(PlayerView.FRAME_TIME * 3f, frames));
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
        this.assetManager.load("PwrRandomUp.png", Texture.class);
        this.assetManager.load("PwrRandomDown.png", Texture.class);
        this.assetManager.load("UI_Box00.png", Texture.class);
        this.assetManager.load("UI_Box01.png", Texture.class);
        this.assetManager.load("UI_Box02.png", Texture.class);
        this.assetManager.load("UI_Box03.png", Texture.class);
        this.assetManager.load("UI_Box00DC.png", Texture.class);
        this.assetManager.load("UI_Box01DC.png", Texture.class);
        this.assetManager.load("UI_Box02DC.png", Texture.class);
        this.assetManager.load("UI_Box03DC.png", Texture.class);
        this.assetManager.load("PlayLobby.png", Texture.class);
        this.assetManager.load("PlayLobby_down.png", Texture.class);
        this.assetManager.load("ExitLobby.png", Texture.class);
        this.assetManager.load("ExitLobby_down.png", Texture.class);
        this.assetManager.load("StunPower.png", Texture.class);
        this.assetManager.load("StunningAnimation22x35.png", Texture.class);
        this.assetManager.load("Background1920x1920.jpg", Texture.class);
        this.assetManager.load("ExplosionAnimation.png", Texture.class);
        this.assetManager.load("WinningScreenBackground.png", Texture.class);
        this.assetManager.load("back-button-md.png", Texture.class);
        this.assetManager.finishLoading();
    }

    public Animation<TextureRegion> getBombAnimation() {
        return bombAnimation;
    }

    public Animation<TextureRegion> getExplosionAnimation(ExplosionModel.Direction direction) {
        return explosionAnimations.get(direction);
    }

    public Animation<TextureRegion> getPlayerAnimation(PlayerColor playerColor, PlayerAnimType animType) {
        return playerAnimations.get(playerColor).get(animType);
    }
}
