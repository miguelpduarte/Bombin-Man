package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.GameModel;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.BrickModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;
import com.xlpoolsion.server.model.entities.PlayerModel;
import com.xlpoolsion.server.view.entities.BombView;
import com.xlpoolsion.server.view.entities.BrickView;
import com.xlpoolsion.server.view.entities.ExplosionView;
import com.xlpoolsion.server.view.entities.PlayerView;

import java.util.List;

import static com.xlpoolsion.server.controller.GameController.GAME_WIDTH;

public class GameScreen extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = false;

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

    //The ingame viewport is supposed to show the full game + some space for the HUD/UI
    private static final float VIEWPORT_WIDTH = GAME_WIDTH * 1.1f;
    private static final float VIEWPORT_WIDTH_PX = VIEWPORT_WIDTH / PIXEL_TO_METER;

    private XLPOOLsionServer xlpooLsionServer;
    private Viewport viewport;

    //Replace by viewfactory?
    private PlayerView playerView;
    private BombView bombView;
    private ExplosionView explosionView;
    private BrickView brickView;


    public GameScreen(XLPOOLsionServer xlpooLsionServer) {
        this.xlpooLsionServer = xlpooLsionServer;

        loadAssets();

        playerView = new PlayerView(xlpooLsionServer);
        bombView = new BombView(xlpooLsionServer);
        explosionView = new ExplosionView(xlpooLsionServer);
        brickView = new BrickView(xlpooLsionServer);

        //TODO: Check how to change this in order to have a higher resolution shown
        //Creating a viewport with consistent aspect ratio
        viewport = new FitViewport(VIEWPORT_WIDTH_PX, VIEWPORT_WIDTH_PX * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

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

    private void drawEntities() {
        //TODO: ViewFactory?

        List<BombModel> bombs = GameModel.getInstance().getBombs();
        for(BombModel bomb : bombs) {
            bombView.update(bomb);
            bombView.draw(xlpooLsionServer.getBatch());
        }

        List<ExplosionModel> explosions = GameModel.getInstance().getExplosions();
        for(ExplosionModel explosion : explosions) {
            explosionView.update(explosion);
            explosionView.draw(xlpooLsionServer.getBatch());
        }
        List<BrickModel> bricks = GameModel.getInstance().getBricks();
        for(BrickModel brick : bricks) {
            brickView.update(brick);
            brickView.draw(xlpooLsionServer.getBatch());
        }

        //Drawing player in end so that he stays on top
        PlayerModel playerModel = GameModel.getInstance().getPlayer();
        playerView.update(playerModel);
        playerView.draw(xlpooLsionServer.getBatch());
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameController.getInstance().movePlayerUp(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().movePlayerRight(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            GameController.getInstance().movePlayerDown(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameController.getInstance().movePlayerLeft(delta);
        } else {
            GameController.getInstance().stopPlayer(delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //Change to specific player when networking is done
            GameController.getInstance().addBomb(GameModel.getInstance().getPlayer());
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
    }
}
