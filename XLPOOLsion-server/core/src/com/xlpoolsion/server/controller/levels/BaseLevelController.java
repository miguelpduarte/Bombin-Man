package com.xlpoolsion.server.controller.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.controller.CollisionController;
import com.xlpoolsion.server.controller.entities.*;
import com.xlpoolsion.server.model.entities.*;
import com.xlpoolsion.server.model.levels.BaseLevelModel;

import java.util.List;

import static com.xlpoolsion.server.controller.GameController.MAX_PLAYERS;

public abstract class BaseLevelController {
    private final World world;
    private PlayerBody[] players = new PlayerBody[MAX_PLAYERS];
    private BaseLevelModel levelModel;

    public BaseLevelController(boolean[] connectedPlayers, BaseLevelModel levelModel) {
        world = new World(new Vector2(0, 0), true);
        this.levelModel = levelModel;

        //Creating bodies
        createPlayers(connectedPlayers);
        createBricks();
        createBreakableBricks();

        world.setContactListener(CollisionController.getInstance());
    }

    private void createBricks() {
        List<BrickModel> brickModels = levelModel.getBricks();
        for(BrickModel brickModel : brickModels) {
            new BrickBody(world, brickModel);
        }
    }

    private void createBreakableBricks() {
        List<BreakableBrickModel> breakableBrickModels = levelModel.getBreakableBricks();
        for(BreakableBrickModel breakableBrickModel : breakableBrickModels) {
            new BreakableBrickBody(world, breakableBrickModel);
        }
    }

    private void createPlayers(boolean[] connectedPlayers) {
        System.out.println("Base Controller creating players");
        for(int i = 0; i < connectedPlayers.length; ++i) {
            if(connectedPlayers[i]) {
                System.out.println("Player " + i + " is connected");
                players[i] = new PlayerBody(world, levelModel.getPlayer(i));
            }
        }
    }

    private float accumulator = 0;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    public void update(float delta) {
        levelModel.update(delta);

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

    public void movePlayer(int playerId, Vector2 move_direction, float delta) {
        if(move_direction.isZero()) {
            stopPlayerX(playerId, delta);
            stopPlayerY(playerId, delta);
            setPlayerMoving(playerId, false);
            return;
        }

        setPlayerMoving(playerId, true);

        if(move_direction.x > 0) {
            movePlayerRight(playerId, delta);
        } else if(move_direction.x != 0) {
            movePlayerLeft(playerId, delta);
        } else {
            stopPlayerX(playerId, delta);
        }

        if(move_direction.y > 0) {
            movePlayerUp(playerId, delta);
        } else if(move_direction.y != 0) {
            movePlayerDown(playerId, delta);
        } else {
            stopPlayerY(playerId, delta);
        }
    }

    private void movePlayerUp(int playerId, float delta) {
        players[playerId].moveUp();
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.UP);
    }

    private void movePlayerDown(int playerId, float delta) {
        players[playerId].moveDown();
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.DOWN);
    }

    private void movePlayerLeft(int playerId, float delta) {
        players[playerId].moveLeft();
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.LEFT);
    }

    private void movePlayerRight(int playerId, float delta) {
        players[playerId].moveRight();
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.RIGHT);
    }

    private void stopPlayerX(int playerId, float delta) {
        players[playerId].stopX();
    }

    private void stopPlayerY(int playerId, float delta) {
        players[playerId].stopY();
    }

    private void setPlayerMoving(int playerId, boolean isMoving) {
        ((PlayerModel) players[playerId].getUserData()).setMoving(isMoving);
    }

    public void placeBomb(int playerId) {
        //TODO: Time and bomb limit verifications
        BombModel bomb = levelModel.createBomb(playerId);
        //No need to do anything with the declared body, as it is stored in the world
        new BombBody(world, bomb);
    }

    public void removeFlagged() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (((EntityModel)body.getUserData()).isFlaggedForRemoval()) {
                levelModel.remove((EntityModel) body.getUserData());
                world.destroyBody(body);
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public void createExplosions(BombModel bomb) {
        List<ExplosionModel> explosions = levelModel.createExplosions(bomb);
        for(ExplosionModel explosion : explosions) {
            new ExplosionBody(world, explosion);
        }
    }

    public BaseLevelModel getModel() {
        return levelModel;
    }
}
