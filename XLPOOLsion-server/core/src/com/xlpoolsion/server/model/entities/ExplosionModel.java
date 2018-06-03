package com.xlpoolsion.server.model.entities;

/**
 * A model representing a single Explosion with a certain direction
 */
public class ExplosionModel extends EntityModel {
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;
    public static final float EXPLOSION_DECAY_TIME = 1f;
    private Direction direction;

    /**
     * The different types of explosions
     */
    public enum Direction {
        Vertical,
        Horizontal,
        Center,
    }

    private float time_to_decay = EXPLOSION_DECAY_TIME;

    /**
     * Creates a new Explosion model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public ExplosionModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    /**
     * Time left for the explosion to be removed
     * @return time left
     */
    public float getTimeToDecay() {
        return time_to_decay;
    }

    /**
     * The type/diretion of the explosion
     * @return The direction of the explosion
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set the direction of the explosion
     * @param direction the direction of the explosion
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }

    /**
     * Sets the time left to decay
     * @param time_to_decay time left
     */
    public void setTimeToDecay(float time_to_decay) {
        this.time_to_decay = time_to_decay;
    }

    /**
     * Lowers the time left to decay
     * @param delta The size of this physics step in seconds.
     * @return True if the time left is below 0
     */
    public boolean decreaseTimeToDecay(float delta) {
        time_to_decay -= delta;
        return time_to_decay < 0;
    }
}
