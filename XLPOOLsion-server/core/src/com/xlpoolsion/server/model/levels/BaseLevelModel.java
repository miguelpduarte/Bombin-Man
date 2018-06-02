package com.xlpoolsion.server.model.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.*;
import com.xlpoolsion.server.view.entities.ViewFactory;

import java.util.ArrayList;
import java.util.List;

import static com.xlpoolsion.server.controller.levels.BaseLevelController.*;

public abstract class BaseLevelModel {
    private PlayerModel[] players = new PlayerModel[GameController.MAX_PLAYERS];

    /**
     * A pool of bombs
     */
    private Pool<BombModel> bombPool = new Pool<BombModel>() {
        @Override
        protected BombModel newObject() {
            return new BombModel(0, 0, 0);
        }
    };

    /**
     * A pool of explosions
     */
    private Pool<ExplosionModel> explosionPool = new Pool<ExplosionModel>() {
        @Override
        protected ExplosionModel newObject() {
            return new ExplosionModel(0, 0, 0);
        }
    };

    private ArrayList<BombModel> bombs = new ArrayList<BombModel>();
    private ArrayList<ExplosionModel> explosions = new ArrayList<ExplosionModel>();
    private ArrayList<BrickModel> bricks = new ArrayList<BrickModel>();
    private ArrayList<BreakableBrickModel> breakableBricks = new ArrayList<BreakableBrickModel>();
    private ArrayList<PowerUpModel> powerUps = new ArrayList<PowerUpModel>();
    private ArrayList<PowerDownModel> powerDowns = new ArrayList<PowerDownModel>();
    private EntityModel[][] brickMatrix = new EntityModel[GRID_END_X_BRICKS - GRID_START_X_BRICKS][GRID_END_Y_BRICKS - GRID_START_Y_BRICKS];
    private ArrayList<StunPowerModel> stunPowers = new ArrayList<StunPowerModel>();

    /**
     * Constructs a Level Model with the indicated players that will be created in the indicated spawns. createBricks and createBreakableBricks are template methods
     * @param connectedPlayers Boolean array to indicate which players are connected
     * @param playerSpawns Array of player spawns. Coordinates are relative to the grid
     */
    public BaseLevelModel(boolean[] connectedPlayers, Vector2[] playerSpawns) {
        createPlayers(connectedPlayers, playerSpawns);
        createBricks();
        createBreakableBricks();
    }

    protected abstract void createBreakableBricks();

    protected abstract void createBricks();

    private void createPlayers(boolean[] connectedPlayers, Vector2[] playerSpawns) {
        if(playerSpawns.length < GameController.MAX_PLAYERS) {
            throw new IllegalArgumentException("Not enough player spawns");
        }

        for(int i = 0; i < GameController.MAX_PLAYERS; ++i) {
            if(connectedPlayers[i]) {
                players[i] = new PlayerModel(playerSpawns[i].x * GRID_PADDING_X, playerSpawns[i].y * GRID_PADDING_Y, 0, i);
            }
        }
    }

    /**
     * Creates a bomb at the player coordinates
     *
     * @param playerId The id of the player creating the bomb
     * @return The created BombModel
     */
    public BombModel createBomb(int playerId) {
        BombModel bomb = bombPool.obtain();

        bomb.setWalkable(true);
        bomb.setFlaggedForRemoval(false);
        //So that the bomb is created at the feet of the player instead at his head
        Vector2 bombPos = snapBombToGrid(players[playerId].getX() + PlayerModel.WIDTH/2, players[playerId].getY() - (PlayerModel.HEIGHT / 2) * 0.4f + PlayerModel.HEIGHT/4);
        bomb.setPosition(bombPos.x, bombPos.y);
        bomb.setRotation(players[playerId].getRotation());
        bomb.setTimeToExplosion(BombModel.EXPLOSION_DELAY);
        bomb.setOwner(players[playerId]);

        bombs.add(bomb);
        return bomb;
    }

    public void update(float delta) {
        for (BombModel bomb : bombs) {
            if (bomb.decreaseTimeToExplosion(delta)) {
                bomb.setFlaggedForRemoval(true);
                GameController.getInstance().createExplosions(bomb);
            }
        }

        for (ExplosionModel explosion : explosions) {
            if (explosion.decreaseTimeToDecay(delta)) {
                explosion.setFlaggedForRemoval(true);
            }
        }

        for (PlayerModel playerModel : players) {
            if(playerModel != null){
                if(playerModel.isDying()){
                    if(playerModel.decreaseTimeTillDeath(delta)){
                        playerModel.setFlaggedForRemoval(true);
                    }
                }
            }
        }
    }

    static final int GRID_START_X_BRICKS = 0;
    static final int GRID_START_Y_BRICKS = 0;
    static final float GRID_START_X = GRID_START_X_BRICKS * BrickModel.WIDTH;
    static final float GRID_START_Y = GRID_START_Y_BRICKS * BrickModel.HEIGHT;
    static final int GRID_END_X_BRICKS = LEVEL_WIDTH_BRICKS;
    static final int GRID_END_Y_BRICKS = LEVEL_HEIGHT_BRICKS;
    static final float GRID_END_X = LEVEL_WIDTH;
    static final float GRID_END_Y = LEVEL_HEIGHT;
    static final float GRID_PADDING_X = BrickModel.WIDTH;
    static final float GRID_PADDING_Y = BrickModel.HEIGHT;

    private Vector2 snapBombToGrid(float x, float y) {
        int x_k = (int) ((x - GRID_START_X)/GRID_PADDING_X);
        int y_k = (int) ((y - GRID_START_Y)/GRID_PADDING_Y);

        return new Vector2(GRID_START_X + x_k * GRID_PADDING_X,GRID_START_Y+ y_k * GRID_PADDING_Y);
    }

    /**
     * Creates an explosion at the bomb coordinates, with the associated player radius
     *
     * @return The created ExplosionModels
     */
    public List<ExplosionModel> createExplosions(BombModel bomb) {
        ArrayList<ExplosionModel> temp_explosions = new ArrayList<ExplosionModel>();

        final int explosion_radius = bomb.getOwner().getExplosionRadius();

        //Creating Center
        Vector2 origin = new Vector2(bomb.getX(), bomb.getY());
        temp_explosions.add(createSingleExplosion(origin,ExplosionModel.Direction.Center));

        //Creating Up
        List<ExplosionModel> upExplosions = createExplosionHelper(origin, new Vector2(0, GRID_PADDING_Y), explosion_radius,ExplosionModel.Direction.Vertical);
        temp_explosions.addAll(upExplosions);

        //Creating Down
        List<ExplosionModel> downExplosions = createExplosionHelper(origin, new Vector2(0, -GRID_PADDING_Y), explosion_radius,ExplosionModel.Direction.Vertical);
        temp_explosions.addAll(downExplosions);

        //Creating Left
        List<ExplosionModel> leftExplosions = createExplosionHelper(origin, new Vector2(-GRID_PADDING_X, 0), explosion_radius,ExplosionModel.Direction.Horizontal);
        temp_explosions.addAll(leftExplosions);

        //Creating Right
        List<ExplosionModel> rightExplosions = createExplosionHelper(origin, new Vector2(GRID_PADDING_X, 0), explosion_radius,ExplosionModel.Direction.Horizontal);
        temp_explosions.addAll(rightExplosions);

        explosions.addAll(temp_explosions);
        return temp_explosions;
    }

    private List<ExplosionModel> createExplosionHelper(Vector2 origin, Vector2 shift, int explosionRadius,ExplosionModel.Direction direction) {
        ArrayList<ExplosionModel> explosions = new ArrayList<ExplosionModel>();
        EntityModel fetchedBrick;

        for (int i = 1; i <= explosionRadius; ++i) {
            Vector2 tempvec = new Vector2(origin);
            tempvec.mulAdd(shift, i);
            if((fetchedBrick = getBrickAt(tempvec.x, tempvec.y)) == null) {
                //No brick, continue creating explosions
                explosions.add(createSingleExplosion(tempvec,direction));
            } else {
                //Found brick, stop creating explosions, and if brick is destroyable mark it for destruction
                //(explosions destroy adjacent bricks but do not propagate there)
                if(fetchedBrick instanceof  BreakableBrickModel) {
                    fetchedBrick.setFlaggedForRemoval(true);
                }
                break;
            }
        }

        return explosions;
    }

    private EntityModel getBrickAt(float x, float y) {
        if(x < GRID_START_X || y < GRID_START_Y || x >= GRID_END_X || y >= GRID_END_Y) {
            return null;
        } else {
            return brickMatrix[Math.round((x - GRID_START_X) / GRID_PADDING_X)][Math.round((y - GRID_START_Y) / GRID_PADDING_Y)];
        }
    }

    private ExplosionModel createSingleExplosion(Vector2 coordinates,ExplosionModel.Direction direction) {
        ExplosionModel explosion = explosionPool.obtain();
        explosion.setDirection(direction);
        explosion.setFlaggedForRemoval(false);
        explosion.setPosition(coordinates.x, coordinates.y);
        //explosion.setDirection(something With The CoordinateVector and maybe something passed from createExplosionHelper)
        explosion.setRotation(0);
        explosion.setTimeToDecay(ExplosionModel.EXPLOSION_DECAY_TIME);
        return explosion;
    }

    public void remove(EntityModel model) {
        //Destroying the no longer necessary view for this model
        ViewFactory.destroyView(model);

        if (model instanceof BombModel) {
            ((BombModel) model).getOwner().decrementActiveBombs();
            bombs.remove(model);
            bombPool.free((BombModel) model);
        } else if (model instanceof ExplosionModel) {
            explosions.remove(model);
            explosionPool.free((ExplosionModel) model);
        } else if (model instanceof BreakableBrickModel) {
            createPowersOnChance((BreakableBrickModel) model);

            //Deleting the breakable brick from the brick matrix and from the breakable brick arraylist
            brickMatrix[Math.round((model.getX() - GRID_START_X)/GRID_PADDING_X)][Math.round((model.getY() - GRID_START_Y)/GRID_PADDING_Y)] = null;
            breakableBricks.remove(model);
        } else if (model instanceof PlayerModel) {
            players[((PlayerModel) model).getId()] = null;
        } else if (model instanceof PowerUpModel) {
            powerUps.remove(model);
        } else if (model instanceof PowerDownModel) {
            powerDowns.remove(model);
        } else if (model instanceof StunPowerModel) {
            stunPowers.remove(model);
        }
    }

    private static final float POWERUP_CHANCE = 0.4f;
    private static final float POWERDOWN_CHANCE = 0.1f;
    private static final float STUNPOWER_CHANCE = 0.2f;

    private void createPowersOnChance(BreakableBrickModel model) {
        if(Math.random() < POWERUP_CHANCE){
            GameController.getInstance().createPowerUp(model);
        } else if(Math.random() < POWERDOWN_CHANCE){
            GameController.getInstance().createPowerDown(model);
        } else if(Math.random() < STUNPOWER_CHANCE) {
            GameController.getInstance().createStunPower(model);
        }
    }

    /**
     * Creates a brick at the given coordinates (in bricks) and adds it to internal storage
     *
     * @param x x Coordinate in bricks
     * @param y y Coordinate in bricks
     */
    public void createBrick(int x, int y) {
        BrickModel brick = new BrickModel(x * BrickModel.WIDTH, y * BrickModel.HEIGHT, 0);

        brick.setFlaggedForRemoval(false);
        brick.setPosition(x * BrickModel.WIDTH, y * BrickModel.HEIGHT);

        bricks.add(brick);
        if(brickMatrix[x][y] != null) {
            throw new IllegalArgumentException("Brick doubly created at: " + x + ", " + y);
        }
        brickMatrix[x][y] = brick;
    }

    /**
     * Creates a Breakable brick at the given coordinates (in bricks) and adds it to internal storage
     *
     * @param x x Coordinate in bricks
     * @param y y Coordinate in bricks
     */
    public void createBreakableBrick(int x, int y) {
        BreakableBrickModel breakablebrick = new BreakableBrickModel(x * BreakableBrickModel.WIDTH, y * BreakableBrickModel.HEIGHT, 0);

        breakablebrick.setFlaggedForRemoval(false);
        breakablebrick.setPosition(x * BreakableBrickModel.WIDTH, y * BreakableBrickModel.HEIGHT);

        breakableBricks.add(breakablebrick);
        if(brickMatrix[x][y] != null) {
            throw new IllegalArgumentException("Breakable brick doubly created at: " + x + ", " + y);
        }
        brickMatrix[x][y] = breakablebrick;
    }

    /**
     * Creates a powerUp at the given coordinates and adds it to internal storage
     *
     * @param brick The Model that was destroid to give room for the powerUp
     */
    public PowerUpModel createPowerUp(BreakableBrickModel brick) {
        PowerUpModel powerUp = new PowerUpModel(brick.getX(), brick.getY(), 0);
        powerUps.add(powerUp);
        return powerUp;
    }

    /**
     * Creates a powerDown at the given coordinates and adds it to internal storage
     *
     * @param brick The Model that was destroid to give room for the powerDown
     */
    public PowerDownModel createPowerDown(BreakableBrickModel brick) {
        PowerDownModel powerDown = new PowerDownModel(brick.getX(), brick.getY(), 0);
        powerDowns.add(powerDown);
        return powerDown;
    }

    /**
     * Creates a stunPower at the given coordinates and adds it to internal storage
     * @param brick The destroyed brick where this power will appear
     * @return The created stun power
     */
    public StunPowerModel createStunPower(BreakableBrickModel brick) {
        StunPowerModel stunPower = new StunPowerModel(brick.getX(), brick.getY(), 0);
        stunPowers.add(stunPower);
        return stunPower;
    }

    public PlayerModel getPlayer(int playerId) {
        return players[playerId];
    }

    public PlayerModel[] getPlayers() {
        return players;
    }

    public List<BombModel> getBombs() {
        return bombs;
    }

    public List<ExplosionModel> getExplosions() {
        return explosions;
    }

    public List<BreakableBrickModel> getBreakableBricks() {
        return breakableBricks;
    }

    public List<BrickModel> getBricks() {
        return bricks;
    }

    public List<PowerUpModel> getPowerUps() {
        return powerUps;
    }

    public List<PowerDownModel> getPowerDowns() {
        return powerDowns;
    }

    public List<StunPowerModel> getStunPowers() {
        return stunPowers;
    }

    public int getNrAlivePlayers() {
        int n_players = 0;
        for(PlayerModel player : players) {
            if(player != null) {
                n_players++;
            }
        }
        return n_players;
    }
}
