package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.*;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.entities.EntityView;
import com.xlpoolsion.server.view.entities.RadiusUpView;
import com.xlpoolsion.server.view.entities.ViewFactory;

import java.util.List;

import static com.xlpoolsion.server.controller.levels.BaseLevelController.LEVEL_WIDTH;

public class GameScreen extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    //Adjust according to the player height in the future
    public static final float PIXEL_TO_METER = 0.08f;

    private XLPOOLsionServer xlpooLsionServer;
    private Viewport viewport;

    private BitmapFont main_size20;

    public GameScreen(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        loadAssets();

        viewport = new ScreenViewport();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = viewport.getCamera().combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }
    }

    private void loadAssets() {
        ///Moved to TextureManager
        //this.xlpooLsionServer.getAssetManager().load("badlogic.jpg", Texture.class);
        //this.xlpooLsionServer.getAssetManager().finishLoading();
        loadFont();
    }

    private void loadFont() {
        FreetypeFontLoader.FreeTypeFontLoaderParameter candl_regular_20px = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        candl_regular_20px.fontFileName = "fonts/Champagne & Limousines.ttf";
        candl_regular_20px.fontParameters.size = 20;
        xlpooLsionServer.getAssetManager().load("main_size20.ttf", BitmapFont.class, candl_regular_20px);
        xlpooLsionServer.getAssetManager().finishLoading();

        main_size20 = this.xlpooLsionServer.getAssetManager().get("main_size20.ttf", BitmapFont.class);
    }

    @Override
    public void render(float delta) {
        GameController.getInstance().removeFlagged();

        handleInputs(delta);

        GameController.getInstance().update(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        xlpooLsionServer.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        xlpooLsionServer.getBatch().begin();
        drawEntities();
        xlpooLsionServer.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = viewport.getCamera().combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    public static final float ENTITY_VIEW_X_SHIFT = Gdx.graphics.getWidth()/2;
    public static final float ENTITY_VIEW_Y_SHIFT = Gdx.graphics.getHeight()/2;

    private void drawEntities() {
        drawBackground();
        drawHUD(GameController.getInstance().getLevelModel().getPlayers());

        List<BombModel> bombs = GameController.getInstance().getLevelModel().getBombs();
        for(BombModel bomb : bombs) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, bomb);
            view.update(bomb);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<ExplosionModel> explosions = GameController.getInstance().getLevelModel().getExplosions();
        for(ExplosionModel explosion : explosions) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, explosion);
            view.update(explosion);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<PowerUpModel> powerUps = GameController.getInstance().getLevelModel().getPowerUps();
        for(PowerUpModel powerUp : powerUps) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, powerUp);
            view.update(powerUp);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<PowerDownModel> powerDowns = GameController.getInstance().getLevelModel().getPowerDowns();
        for(PowerDownModel powerDown : powerDowns) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, powerDown);
            view.update(powerDown);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<StunPowerModel> stunPowers = GameController.getInstance().getLevelModel().getStunPowers();
        for(StunPowerModel stunPower : stunPowers) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, stunPower);
            view.update(stunPower);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<BreakableBrickModel> breakablebricks = GameController.getInstance().getLevelModel().getBreakableBricks();
        for(BreakableBrickModel breakablebrick : breakablebricks) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, breakablebrick);
            view.update(breakablebrick);
            view.draw(xlpooLsionServer.getBatch());
        }

        List<BrickModel> bricks = GameController.getInstance().getLevelModel().getBricks();
        for(BrickModel brick : bricks) {
            EntityView view = ViewFactory.getView(xlpooLsionServer, brick);
            view.update(brick);
            view.draw(xlpooLsionServer.getBatch());
        }


        //Drawing player in end so that he stays on top
        PlayerModel[] players = GameController.getInstance().getLevelModel().getPlayers();
        for(int i = 0; i < GameController.MAX_PLAYERS; ++i) {
            if(players[i] == null) {
                continue;
            }
            EntityView view = ViewFactory.getView(xlpooLsionServer, players[i]);
            view.update(players[i]);
            view.draw(xlpooLsionServer.getBatch());
        }
    }

    private void drawBackground() {
        Texture backgroundTex = xlpooLsionServer.getAssetManager().get("Background1920x1920.jpg");
        Sprite background = new Sprite(backgroundTex);
        background.setSize(1920, 1920);
        background.draw(xlpooLsionServer.getBatch());

    }

    private static final float HUD_WIDTH = 160;
    private static final float HUD_HEIGHT = 96;
    private static final float HUD_GAP = 165;
    private static final float SCALE_POWER = 1.5f;
    private static final float HUD_POWER_SIZE = RadiusUpView.WIDTH / SCALE_POWER;

    private void drawHUD(PlayerModel[] players) {
        for (int i = 0; i < GameController.MAX_PLAYERS; i++) {
            float hud_x = ENTITY_VIEW_X_SHIFT - (LEVEL_WIDTH / 2) / PIXEL_TO_METER + HUD_GAP / 2 + i * HUD_GAP;
            float hud_y = ENTITY_VIEW_Y_SHIFT + (LEVEL_WIDTH / 2) / PIXEL_TO_METER + HUD_HEIGHT;

            if (players[i] != null && !players[i].isDying()) {
                Texture uiBox = xlpooLsionServer.getAssetManager().get("UI_Box0" + i + ".png");
                Sprite uiSpriteBox = new Sprite(uiBox);
                uiSpriteBox.setSize(HUD_WIDTH, HUD_HEIGHT);
                uiSpriteBox.setCenter(hud_x, hud_y);
                uiSpriteBox.setRotation(0);
                uiSpriteBox.draw(xlpooLsionServer.getBatch());

                drawPowerHud(PowerUpModel.PowerUpType.BombRadUp, hud_x + HUD_POWER_SIZE / 2, hud_y + HUD_POWER_SIZE);
                drawPowerHud(PowerUpModel.PowerUpType.BombsUp, hud_x + HUD_POWER_SIZE / 2, hud_y);
                drawPowerHud(PowerUpModel.PowerUpType.SpeedUp, hud_x + HUD_POWER_SIZE / 2, hud_y - HUD_POWER_SIZE);
                drawAmount(players[i], hud_x, hud_y - HUD_POWER_SIZE);
            } else {
                Texture uiBox = xlpooLsionServer.getAssetManager().get("UI_Box0" + i + "DC.png");
                Sprite uiSpriteBox = new Sprite(uiBox);
                uiSpriteBox.setSize(HUD_WIDTH, HUD_HEIGHT);
                uiSpriteBox.setCenter(hud_x, hud_y);
                uiSpriteBox.setRotation(0);
                uiSpriteBox.draw(xlpooLsionServer.getBatch());

            }


        }
    }

    private void drawAmount(PlayerModel player, float hud_x, float hud_y) {
        int[] amounts = new int[] {
                player.getSpeedChanger(), player.getAllowedBombsChanger(), player.getExplosionChanger()
        };

        for(int i = 0; i < amounts.length; i++){
            //nrSprite.setSize(HUD_POWER_SIZE/2, HUD_POWER_SIZE);
            //nrSprite.setCenter(hud_x + HUD_POWER_SIZE * 2, hud_y + i * HUD_POWER_SIZE);
            main_size20.draw(xlpooLsionServer.getBatch(), "" + amounts[i],
                    hud_x + HUD_POWER_SIZE, hud_y + i * HUD_POWER_SIZE + HUD_POWER_SIZE/4,
                    HUD_POWER_SIZE*2, Align.center, false);
        }


    }

    private void drawPowerHud(PowerUpModel.PowerUpType power, float hud_x, float hud_y){
        Texture powerTex;
        switch (power){
            case BombsUp:
                powerTex = xlpooLsionServer.getAssetManager().get("PwrBombUp.png");
                break;
            case SpeedUp:
                powerTex = xlpooLsionServer.getAssetManager().get("PwrSpeedUp.png");
                break;
            case BombRadUp:
                powerTex = xlpooLsionServer.getAssetManager().get("PwrRadiusUp.png");
                break;
            default:
                powerTex = xlpooLsionServer.getAssetManager().get("PwrRadiusUp.png");
                break;
        }

        Sprite pwrSprite = new Sprite(powerTex);
        pwrSprite.setSize(HUD_POWER_SIZE, HUD_POWER_SIZE);
        pwrSprite.setCenter(hud_x + HUD_POWER_SIZE / 2, hud_y);
        pwrSprite.setRotation(0);
        pwrSprite.draw(xlpooLsionServer.getBatch());
    }

    private static final boolean usingMobile = true;

    private void handleInputs(float delta) {
        if(usingMobile) {
            return;
        }

        Vector2 move_dir = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            move_dir.y = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            move_dir.y = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move_dir.x = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            move_dir.x = -1;
        }

        GameController.getInstance().movePlayer(0, move_dir);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            GameController.getInstance().placeBomb(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        //TODO: Move to transition to another screen
        NetworkRouter.getInstance().closeServer();
    }
}
