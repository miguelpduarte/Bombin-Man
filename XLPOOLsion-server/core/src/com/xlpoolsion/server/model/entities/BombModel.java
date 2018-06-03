package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.BombView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 *  A model representing a single Bomb
 */
public class BombModel extends EntityModel {
    /**
     * Width of the model
     */
    public static final float WIDTH = BombView.WIDTH * PIXEL_TO_METER;
    /**
     * Height of the model
     */
    public static final float HEIGHT = BombView.HEIGHT * PIXEL_TO_METER;
    /**
     * Time it takes for the explosion to decay
     */
    public static final float EXPLOSION_DELAY = 3.0f;

    private PlayerModel owner_player;

    private float time_to_explosion;
    private boolean walkable = true;

    /**
     * Creates a new Bomb model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public BombModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    /**
     * Decreases the time left for the bomb to explode
     * @param delta The size of this physics step in seconds.
     * @return True if the time is below 0 and the bomb should explode
     */
    public boolean decreaseTimeToExplosion(float delta) {
        time_to_explosion -= delta;
        return time_to_explosion < 0;
    }

    /**
     * Sets the time for the bomb to explode
     * @param time_to_explosion time left to explode
     */
    public void setTimeToExplosion(float time_to_explosion) {
        this.time_to_explosion = time_to_explosion;
    }

    /**
     * Returns whether the bomb can be walked on
     * @return True if the bomb can be walked on, false if not
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * Changes the Walkable value
     * @param walkable value that walkable will be changed
     */
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    /**
     * Sets the owner of the bomb
     * @param owner Player that owns the bomb
     */
    public void setOwner(PlayerModel owner) {
        this.owner_player = owner;
    }

    /**
     * Gets the owner of the bomb
     * @return Player that owns the bomb
     */
    public PlayerModel getOwner() {
        return owner_player;
    }
}
