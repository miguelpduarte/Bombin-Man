package com.xlpoolsion.server.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.*;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static GameModel instance = null;
    private PlayerModel player;
    private ArrayList<BombModel> bombs;
    private ArrayList<ExplosionModel> explosions;
    private ArrayList<BrickModel> bricks;
    private ArrayList<BreakableBrickModel> breakableBricks;

    /**
     * A pool of bombs
     */
    Pool<BombModel> bombPool = new Pool<BombModel>() {
        @Override
        protected BombModel newObject() {
            return new BombModel(0, 0, 0);
        }
    };

    /**
     * A pool of explosions
     */
    Pool<ExplosionModel> explosionPool = new Pool<ExplosionModel>() {
        @Override
        protected ExplosionModel newObject() {
            return new ExplosionModel(0, 0, 0);
        }
    };

    private GameModel() {
        player = new PlayerModel(GameController.GAME_WIDTH / 2, GameController.GAME_HEIGHT / 2, 0);
        bombs = new ArrayList<BombModel>();
        explosions = new ArrayList<ExplosionModel>();
        bricks = new ArrayList<BrickModel>();
        breakableBricks = new ArrayList<BreakableBrickModel>();
    }


    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    public PlayerModel getPlayer() {
        return player;
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
    }

    public void remove(EntityModel model) {
        if (model instanceof BombModel) {
            bombs.remove(model);
            bombPool.free((BombModel) model);
        } else if (model instanceof ExplosionModel) {
            explosions.remove(model);
            explosionPool.free((ExplosionModel) model);
        } else if (model instanceof BreakableBrickModel) {
            breakableBricks.remove(model);
        }
    }
    /**
     * Creates a brick at the given coordinates
     *
     * @param x x Coordinate
     * @param y y Coordinate
     * @return The created BrickModel
     */
    public BrickModel createBrick(float x, float y) {
        BrickModel brick = new BrickModel(x, y, 0);

        brick.setFlaggedForRemoval(false);
        brick.setPosition(x,y);

        bricks.add(brick);
        return brick;
    }

    /**
     * Creates a Breakable brick at the given coordinates
     *
     * @param x x Coordinate
     * @param y y Coordinate
     * @return The created BreakableBrickModel
     */
    public BreakableBrickModel createBreakableBrick(float x, float y) {
        BreakableBrickModel breakablebrick = new BreakableBrickModel(x, y, 0);

        breakablebrick.setFlaggedForRemoval(false);
        breakablebrick.setPosition(x,y);

        breakableBricks.add(breakablebrick);
        return breakablebrick;
    }
    /**
     * Creates a bomb at the player coordinates
     *
     * @param owner_player
     * @return The created BombModel
     */
    public BombModel createBomb(PlayerModel owner_player) {
        BombModel bomb = bombPool.obtain();

        bomb.setWalkable(true);
        bomb.setFlaggedForRemoval(false);
        //So that the bomb is created at the feet of the player instead at his head
        bomb.setPosition(this.player.getX(), this.player.getY() - (PlayerModel.HEIGHT / 2) * 0.4f);
        bomb.setRotation(this.player.getRotation());
        bomb.setTimeToExplosion(BombModel.EXPLOSION_DELAY);
        bomb.setOwner(owner_player);

        bombs.add(bomb);
        return bomb;
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
        temp_explosions.add(createSingleExplosion(origin));

        //Creating Up
        List<ExplosionModel> upExplosions = createExplosionHelper(origin, new Vector2(0, ExplosionModel.HEIGHT), explosion_radius);
        temp_explosions.addAll(upExplosions);

        //Creating Down
        List<ExplosionModel> downExplosions = createExplosionHelper(origin, new Vector2(0, -ExplosionModel.HEIGHT), explosion_radius);
        temp_explosions.addAll(downExplosions);

        //Creating Left
        List<ExplosionModel> leftExplosions = createExplosionHelper(origin, new Vector2(-ExplosionModel.WIDTH, 0), explosion_radius);
        temp_explosions.addAll(leftExplosions);

        //Creating Right
        List<ExplosionModel> rightExplosions = createExplosionHelper(origin, new Vector2(ExplosionModel.WIDTH, 0), explosion_radius);
        temp_explosions.addAll(rightExplosions);

        explosions.addAll(temp_explosions);
        return temp_explosions;
    }

    private List<ExplosionModel> createExplosionHelper(Vector2 origin, Vector2 shift, int explosionRadius) {
        ArrayList<ExplosionModel> explosions = new ArrayList<ExplosionModel>();

        for (int i = 1; i <= explosionRadius; ++i) {
            Vector2 tempvec = new Vector2(origin);
            explosions.add(createSingleExplosion(tempvec.mulAdd(shift, i)));
        }

        return explosions;
    }

    private ExplosionModel createSingleExplosion(Vector2 coordinates) {
        ExplosionModel explosion = explosionPool.obtain();
        explosion.setFlaggedForRemoval(false);
        explosion.setPosition(coordinates.x, coordinates.y);
        explosion.setRotation(0);
        explosion.setTimeToDecay(ExplosionModel.EXPLOSION_DECAY_TIME);
        return explosion;
    }
}
