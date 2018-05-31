package com.xlpoolsion.server.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.*;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.entities.EntityView;
import com.xlpoolsion.server.view.entities.ViewFactory;

import java.util.List;

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
        for(int i = 0; i < GameController.MAX_PLAYERS; ++i) {
            PlayerModel playerModel = GameController.getInstance().getLevelModel().getPlayer(i);
            if(playerModel == null) {
                continue;
            }
            EntityView view = ViewFactory.getView(xlpooLsionServer, playerModel);
            view.update(playerModel);
            view.draw(xlpooLsionServer.getBatch());
        }
    }

    private static boolean usingMobile = true;

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

        GameController.getInstance().movePlayer(0, move_dir, delta);

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
        NetworkRouter.getInstance().closeServer();
    }
}
