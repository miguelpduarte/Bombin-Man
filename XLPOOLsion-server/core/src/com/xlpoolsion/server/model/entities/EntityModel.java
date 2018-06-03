package com.xlpoolsion.server.model.entities;

/**
 * An abstract model representing an entity belonging to a game model.
 */
public class EntityModel {
    private float x = 0;
    private float y = 0;

    private float rotation = 0;
    private boolean flaggedForRemoval = false;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     * @param rotation The current rotation of this entity in radians.
     */
    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Gets the Model's rotation
     * @return the rotation of the model
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the model
     * @param rotation the rotation of the model
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the X coordinate of the model
     * @return x Coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of the model
     * @return y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Set the model position
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Mark the model for removal that will later be removed
     * @param flaggedForRemoval True if marked for removal, false if not
     */
    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    /**
     * Returns whether the model is marked for removal or not
     * @return True if model is marked for removal, false if not
     */
    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }
}
