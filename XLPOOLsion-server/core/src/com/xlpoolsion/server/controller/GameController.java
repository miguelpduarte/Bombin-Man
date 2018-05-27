package com.xlpoolsion.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.controller.entities.*;
import com.xlpoolsion.server.model.GameModel;
import com.xlpoolsion.server.model.entities.*;

import java.util.ArrayList;
import java.util.List;

import static com.xlpoolsion.server.model.GameModel.*;

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
        loadBreakableBricks();

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
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.UP);
    }

    public void movePlayerDown(float delta) {
        player.moveDown();
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.DOWN);
    }

    public void movePlayerLeft(float delta) {
        player.moveLeft();
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.LEFT);
    }

    public void movePlayerRight(float delta) {
        player.moveRight();
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.RIGHT);
    }

    public void stopPlayerX(float delta) {
        player.stopX();
    }

    public void stopPlayerY(float delta) {
        player.stopY();
    }

    public void setPlayerStopped(boolean isStopped) {
        ((PlayerModel) player.getUserData()).setMoving(isStopped);
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
        for(int i = 0; GRID_START_Y + i*GRID_PADDING_Y < GRID_END_Y; i += 4) {
            for(int j = 0; GRID_START_X + j*GRID_PADDING_X < GRID_END_X; j += 6) {
                BrickModel brick = GameModel.getInstance().createBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                new BrickBody(world, brick);
            }
        }
    }

    private void loadBreakableBricks() {
        for(int i = 0; GRID_START_Y + i*GRID_PADDING_Y < GRID_END_Y; ++i) {
            for(int j = 0; GRID_START_X + j*GRID_PADDING_X < GRID_END_X; ++j) {
                if (i % 4 == 0) {
                    //Linha em que tem fixos
                    //Desenhar a nao por um a cada 4, começando a nao por
                    if(j % 6 != 0) {
                        BreakableBrickModel breakablebrick = GameModel.getInstance().createBreakableBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                        new BreakableBrickBody(world, breakablebrick);
                    }
                } else {
                    //Linha sem fixos, desenhar espaçado
                    if(j % 6 == 0) {
                        BreakableBrickModel breakablebrick = GameModel.getInstance().createBreakableBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                        new BreakableBrickBody(world, breakablebrick);
                    }
                }
            }
        }
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

    public void movePlayer(Vector2 move_direction, float deltaTime) {
        boolean startedMovingX = false;
        boolean startedMovingY = false;

        if(move_direction.x > 0) {
            movePlayerRight(deltaTime);
            startedMovingX = true;
        } else if(move_direction.x != 0) {
            movePlayerLeft(deltaTime);
            startedMovingX = true;
        }

        if(move_direction.y > 0) {
            movePlayerUp(deltaTime);
            startedMovingY = true;

        } else if(move_direction.y != 0) {
            movePlayerDown(deltaTime);
            startedMovingY = true;
        }

        if(!startedMovingX) {
            stopPlayerX(deltaTime);
        }

        if(!startedMovingY) {
            stopPlayerY(deltaTime);
        }

        setPlayerStopped(startedMovingX || startedMovingY);
    }
}
