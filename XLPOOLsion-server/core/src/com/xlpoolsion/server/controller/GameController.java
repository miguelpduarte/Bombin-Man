package com.xlpoolsion.server.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.controller.entities.BombBody;
import com.xlpoolsion.server.controller.entities.BrickBody;
import com.xlpoolsion.server.controller.entities.ExplosionBody;
import com.xlpoolsion.server.controller.entities.PlayerBody;
import com.xlpoolsion.server.model.GameModel;
import com.xlpoolsion.server.model.entities.*;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private static GameController instance = null;

    private final World world;
    private final PlayerBody player;
    private ArrayList<BrickBody> brickWalls;

    /**
     * The map width in meters.
     */
    public static final float GAME_WIDTH = 50;

    /**
     * The map height in meters.
     */
    public static final float GAME_HEIGHT = 50;

    private GameController() {
        world = new World(new Vector2(0, 0), true);

        //Creating bodies
        player = new PlayerBody(world, GameModel.getInstance().getPlayer());
        loadWalls();

        world.setContactListener(CollisionController.getInstance());
    }


    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private float accumulator = 0;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    public void update(float delta) {
        GameModel.getInstance().update(delta);

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }

        //Updating bodies
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            //verifyBounds(body);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    public void movePlayerUp(float delta) {
        player.moveUp();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.UP);
    }

    public void movePlayerDown(float delta) {
        player.moveDown();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.DOWN);
    }

    public void movePlayerLeft(float delta) {
        player.moveLeft();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.LEFT);
    }

    public void movePlayerRight(float delta) {
        player.moveRight();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.RIGHT);
    }

    public void stopPlayer(float delta) {
        player.stop();
        ((PlayerModel) player.getUserData()).setMoving(false);
    }

    public void addBomb(PlayerModel owner_player) {
        //TODO: Time and bomb limit verifications
        BombModel bomb = GameModel.getInstance().createBomb(owner_player);
        //No need to do anything with the declared body, as it is stored in the world
        new BombBody(world, bomb);
    }

    public void createExplosions(BombModel bomb) {
        List<ExplosionModel> explosions = GameModel.getInstance().createExplosions(bomb);
        for(ExplosionModel explosion : explosions) {
            new ExplosionBody(world, explosion);
        }
    }
    private void loadWalls() {

        BrickModel brick = GameModel.getInstance().createBrick(1,2);
        new BrickBody(world, brick);

    }

    public void removeFlagged() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (((EntityModel)body.getUserData()).isFlaggedForRemoval()) {
                GameModel.getInstance().remove((EntityModel) body.getUserData());
                world.destroyBody(body);
            }
        }
    }

    /**
     * Just for debug purposes (Debug Camera)
     * @return
     */
    public World getWorld() {
        return world;
    }
}
