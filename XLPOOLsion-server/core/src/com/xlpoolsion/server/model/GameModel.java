package com.xlpoolsion.server.model;

import com.badlogic.gdx.utils.Pool;
import com.xlpoolsion.server.controller.GameController;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static GameModel instance = null;
    private PlayerModel player;
    private ArrayList<BombModel> bombs;

    /**
     * A pool of bombs
     */
    Pool<BombModel> bombPool = new Pool<BombModel>() {
        @Override
        protected BombModel newObject() {
            return new BombModel(0, 0, 0);
        }
    };

    private GameModel() {
        player = new PlayerModel(GameController.GAME_WIDTH/2, GameController.GAME_HEIGHT/2, 0);
        bombs = new ArrayList<BombModel>();
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

    public void update(float delta) {
        //System.out.println("I am Game Model and am updating with delta " + delta);
        for(BombModel bomb : bombs) {
            if(bomb.decreaseTimeToExplosion(delta)) {
                System.out.println("Boom, bomb exploded");
                bomb.setFlaggedForRemoval(true);
            }
        }
    }

    public void remove(EntityModel model) {
        if(model instanceof BombModel) {
            bombs.remove(model);
            System.out.println("Removed a bomb");
        }
    }

    /**
     * Creates a bomb at the player coordinates
     * @return The created BombModel
     */
    public BombModel createBomb() {
        BombModel bomb = bombPool.obtain();

        bomb.setFlaggedForRemoval(false);
        bomb.setPosition(this.player.getX(), this.player.getY());
        bomb.setRotation(this.player.getRotation());
        bomb.setTimeToExplosion(BombModel.EXPLOSION_DELAY);

        bombs.add(bomb);
        return bomb;
    }
}
