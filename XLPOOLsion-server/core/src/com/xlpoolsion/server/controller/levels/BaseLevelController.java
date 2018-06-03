package com.xlpoolsion.server.controller.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.controller.CollisionController;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.controller.entities.*;
import com.xlpoolsion.server.model.entities.*;
import com.xlpoolsion.server.model.levels.BaseLevelModel;

import java.util.List;

import static com.xlpoolsion.server.controller.GameController.MAX_PLAYERS;
import static com.xlpoolsion.server.controller.GameController.MIN_CONNECTED_CLIENTS;

public abstract class BaseLevelController {
    /**
     * The level width in bricks
     */
    public static final int LEVEL_WIDTH_BRICKS = 21;
    /**
     * The level height in bricks
     */
    public static final int LEVEL_HEIGHT_BRICKS = 21;

    /**
     * The level width in meters
     */
    public static final float LEVEL_WIDTH = LEVEL_WIDTH_BRICKS * BrickModel.WIDTH;
    /**
     * The level height in meters
     */
    public static final float LEVEL_HEIGHT = LEVEL_HEIGHT_BRICKS * BrickModel.HEIGHT;

    private final World world;
    private PlayerBody[] players = new PlayerBody[MAX_PLAYERS];
    private BaseLevelModel levelModel;

    BaseLevelController(boolean[] connectedPlayers, BaseLevelModel levelModel) {
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

    /**
     * Game Ticking function during playing. Updates game world (collisions, etc) and game model (bomb ticks, explosion ticks, etc)
     * @param delta Time since the last frame render
     */
    public void update(float delta) {
        levelModel.update(delta);
        //Checking win condition
        if(levelModel.getNrAlivePlayers() < MIN_CONNECTED_CLIENTS) {
            System.out.println("\nTEMPORARILY SAYING PLAYER 0 WON THE GAME, ALWAYS. TO CHANGE!!!!!!\n");
            levelModel.addWinnerToPlayersInfo();
            GameController.getInstance().wonGame(levelModel.getWinner());
            //return;
        }

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
            verifyBounds(body);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    /**
     * Verifies if the body is inside the level bounds and if not
     * limits it to inside the bounds
     *
     * @param body The body to be verified.
     */
    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > LEVEL_WIDTH)
            body.setTransform(LEVEL_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > LEVEL_HEIGHT)
            body.setTransform(body.getPosition().x, LEVEL_HEIGHT, body.getAngle());
    }


    public void movePlayer(int playerId, Vector2 move_direction) {
        if(move_direction.isZero() || levelModel.getPlayer(playerId).isStunned()) {
            stopPlayerX(playerId);
            stopPlayerY(playerId);
            setPlayerMoving(playerId, false);
            return;
        }

        setPlayerMoving(playerId, true);

        if(move_direction.x > 0) {
            movePlayerRight(playerId, move_direction.x);
        } else if(move_direction.x != 0) {
            movePlayerLeft(playerId, move_direction.x);
        } else {
            stopPlayerX(playerId);
        }

        if(move_direction.y > 0) {
            movePlayerUp(playerId, move_direction.y);
        } else if(move_direction.y != 0) {
            movePlayerDown(playerId, move_direction.y);
        } else {
            stopPlayerY(playerId);
        }
    }

    private void movePlayerUp(int playerId, float moveFactor) {
        players[playerId].moveUp(moveFactor);
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.UP);
    }

    private void movePlayerDown(int playerId, float moveFactor) {
        players[playerId].moveDown(moveFactor);
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.DOWN);
    }

    private void movePlayerLeft(int playerId, float moveFactor) {
        players[playerId].moveLeft(moveFactor);
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.LEFT);
    }

    private void movePlayerRight(int playerId, float moveFactor) {
        players[playerId].moveRight(moveFactor);
        ((PlayerModel) players[playerId].getUserData()).setOrientation(PlayerModel.Orientation.RIGHT);
    }

    private void stopPlayerX(int playerId) {
        players[playerId].stopX();
    }

    private void stopPlayerY(int playerId) {
        players[playerId].stopY();
    }

    private void setPlayerMoving(int playerId, boolean isMoving) {
        ((PlayerModel) players[playerId].getUserData()).setMoving(isMoving);
    }

    public void placeBomb(int playerId) {
        PlayerModel player = levelModel.getPlayer(playerId);

        if(!player.isStunned() && !player.isOverBomb() && player.incrementActiveBombs()) {
            BombModel bomb = levelModel.createBomb(playerId);
            //No need to do anything with the declared body, as it is stored in the world
            new BombBody(world, bomb);
            //Bomb placed on player, so it is over the bomb
            player.setOverBomb(true);
        }
    }

    public void removeFlagged() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (((EntityModel)body.getUserData()).isFlaggedForRemoval()) {
                levelModel.remove((EntityModel) body.getUserData());
                //To ensure that there is no wrong access
                if(body.getUserData() instanceof PlayerModel) {
                    players[((PlayerModel) body.getUserData()).getId()] = null;
                }
                world.destroyBody(body);
            }
        }
    }

    /**
     * Used for debug physics camera
     * @return The currently used world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Creates explosions relative to the passed bomb
     * @param bomb Bomb that exploded
     */
    public void createExplosions(BombModel bomb) {
        List<ExplosionModel> explosions = levelModel.createExplosions(bomb);
        for(ExplosionModel explosion : explosions) {
            new ExplosionBody(world, explosion);
        }
    }

    /**
     * Creates a power up based on the given breakable brick model
     * @param brick The brick that was broken and where the power will appear
     */
    public void createPowerUp(BreakableBrickModel brick) {
        PowerUpModel powerUp = levelModel.createPowerUp(brick);
        new PowerUpBody(world,powerUp);
    }

    /**
     * Creates a power down based on the given breakable brick model
     * @param brick The brick that was broken and where the power will appear
     */
    public void createPowerDown(BreakableBrickModel brick) {
        PowerDownModel powerDown = levelModel.createPowerDown(brick);
        new PowerDownBody(world,powerDown);
    }

    /**
     * Creates a stun power based on the given breakable brick model
     * @param brick The brick that was broken and where the power will appear
     */
    public void createStunPower(BreakableBrickModel brick) {
        StunPowerModel stunPower = levelModel.createStunPower(brick);
        new StunPowerBody(world, stunPower);
    }

    /**
     * Getter function for the corresponding level model
     * @return The associated level model
     */
    public BaseLevelModel getModel() {
        return levelModel;
    }

    /**
     * Informs the level controller that a certain player disconnected. Kills the player in the game, effectively
     * @param playerId The player that has disconnected
     */
    public void informPlayerDisconnect(int playerId) {
        PlayerModel player = levelModel.getPlayer(playerId);
        if(player != null && !player.isDying()) {
            player.startDying();
        }
    }

    /**
     * Marks all players except the passed one to be stunned. For use on pickup of StunPower
     * @param stunnerPlayer The player to not stun, for it was the causer of the stunning
     */
    public void setAllStunnedExcept(PlayerModel stunnerPlayer) {
        PlayerModel[] players = levelModel.getPlayers();
        for(int i = 0; i < GameController.MAX_PLAYERS; ++i) {
            if(players[i] == null || players[i].getId() == stunnerPlayer.getId()) {
                continue;
            }
            players[i].stun();
        }
    }

    /**
     * Removes the stunned status effect from a certain player
     * @param playerId The id of the player to unstun
     */
    public void unstun(int playerId) {
        PlayerModel player = levelModel.getPlayer(playerId);
        if(player != null) {
            player.unstun();
        }
    }

    /**
     * Marks everything for disposal by the GC (as much as possible)
     */
    public void destroy() {
        levelModel.destroy();
        levelModel = null;
        for(PlayerBody player : players) {
            player = null;
        }
        players = null;

        world.dispose();
    }
}
