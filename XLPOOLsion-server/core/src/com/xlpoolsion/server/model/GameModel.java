package com.xlpoolsion.server.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static GameModel instance = null;
    private PlayerModel player;
    private ArrayList<BombModel> bombs;
    private ArrayList<ExplosionModel> explosions;

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
        player = new PlayerModel(GameController.GAME_WIDTH/2, GameController.GAME_HEIGHT/2, 0);
        bombs = new ArrayList<BombModel>();
        explosions = new ArrayList<ExplosionModel>();
    }

    public static GameModel getInstance() {
        if(instance == null) {
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

    public void update(float delta) {
        for(BombModel bomb : bombs) {
            if(bomb.decreaseTimeToExplosion(delta)) {
                bomb.setFlaggedForRemoval(true);
                GameController.getInstance().createExplosions(bomb);
            }
        }

        for(ExplosionModel explosion : explosions) {
            if(explosion.decreaseTimeToDecay(delta)) {
                explosion.setFlaggedForRemoval(true);
            }
        }
    }

    public void remove(EntityModel model) {
        if(model instanceof BombModel) {
            bombs.remove(model);
            bombPool.free((BombModel) model);
        } else if(model instanceof ExplosionModel) {
            explosions.remove(model);
            explosionPool.free((ExplosionModel) model);
        }
    }

    /**
     * Creates a bomb at the player coordinates
     * @return The created BombModel
     */
    public BombModel createBomb() {
        BombModel bomb = bombPool.obtain();

        bomb.setWalkable(true);
        bomb.setFlaggedForRemoval(false);
        //So that the bomb is created at the feet of the player instead at his head
        bomb.setPosition(this.player.getX(), this.player.getY() - (PlayerModel.HEIGHT / 2) * 0.4f);
        bomb.setRotation(this.player.getRotation());
        bomb.setTimeToExplosion(BombModel.EXPLOSION_DELAY);

        bombs.add(bomb);
        return bomb;
    }

    /**
     * Creates an explosion at the bomb coordinates, with the associated player radius
     * @return The created ExplosionModels
     */
    public List<ExplosionModel> createExplosions(BombModel bomb) {
        ArrayList<ExplosionModel> temp_explosions = new ArrayList<ExplosionModel>();

        //TODO: Associate with specific Player using BombModel later on, now using global constant

        //Creating Center
        Vector2 origin = new Vector2(bomb.getX(), bomb.getY());
        temp_explosions.add(createSingleExplosion(origin));

        //Creating Up
        List<ExplosionModel> upExplosions = createExplosionHelper(origin, new Vector2(0, ExplosionModel.HEIGHT), PlayerModel.EXPLOSION_RADIUS);
        temp_explosions.addAll(upExplosions);

        //Creating Down
        List<ExplosionModel> downExplosions = createExplosionHelper(origin, new Vector2(0, -ExplosionModel.HEIGHT), PlayerModel.EXPLOSION_RADIUS);
        temp_explosions.addAll(downExplosions);

        //Creating Left
        List<ExplosionModel> leftExplosions = createExplosionHelper(origin, new Vector2(-ExplosionModel.WIDTH, 0), PlayerModel.EXPLOSION_RADIUS);
        temp_explosions.addAll(leftExplosions);

        //Creating Right
        List<ExplosionModel> rightExplosions = createExplosionHelper(origin, new Vector2(ExplosionModel.WIDTH, 0), PlayerModel.EXPLOSION_RADIUS);
        temp_explosions.addAll(rightExplosions);

        explosions.addAll(temp_explosions);
        return temp_explosions;
    }

    private List<ExplosionModel> createExplosionHelper(Vector2 origin, Vector2 shift, int explosionRadius) {
        ArrayList<ExplosionModel> explosions = new ArrayList<ExplosionModel>();

        for(int i = 1; i < explosionRadius; ++i) {
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
