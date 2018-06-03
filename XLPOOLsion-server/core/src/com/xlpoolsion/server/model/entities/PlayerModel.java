package com.xlpoolsion.server.model.entities;

import com.xlpoolsion.server.view.entities.PlayerView;

import static com.xlpoolsion.server.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single Player
 */
public class PlayerModel extends EntityModel {
    public static final float WIDTH = 1.9f;
    public static final float HEIGHT = PlayerView.HEIGHT * 0.8f * PIXEL_TO_METER;

    private boolean moving = false;
    private boolean overBomb = false;
    private boolean stunned = false;
    private boolean dying = false;
    private float time_till_death = DYING_TIMEOUT;
    private Orientation currentOrientation = Orientation.DOWN;

    public static final float DYING_TIMEOUT = 4.0f;
    private static final float STARTING_SPEED = 6f;
    private static final int STARTING_ALLOWED_BOMBS = 1;
    private static final int STARTING_EXPLOSION_RADIUS = 3;
    private static final int MAX_EXPLODING_BONUS = 6;
    private static final int MAX_EXPLODING_PENALTY = -2;
    private static final int MAX_BOMBS_BONUS = 10;
    private static final int MAX_SPEED_BONUS = 6;
    private static final int MAX_SPEED_PENALTY = -2;
    private static final int MAX_BOMBS_PENALTY = 1;
    private static final float SPEED_INCREMENT = 0.6f;

    private int activeBombs = 0;
    private int id;

    //Powerups
    private int speedChanger = 0;
    private int explosionChanger = 0;
    private int allowedBombsChanger = 0;

    /**
     * Creates a new Player model in a certain position and having a certain rotation and id.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     * @param id the Id of the player
     */
    public PlayerModel(float x, float y, float rotation, int id) {
        super(x, y, rotation);
        this.id = id;
    }

    /**
     * Gets the current speed of the player based on the power ups caught
     * @return the speed of the player
     */
    public float getCurrentSpeed() {
        return STARTING_SPEED + (speedChanger * SPEED_INCREMENT);
    }

    /**
     * Returns true if the player is moving
     * @return True if the player is moving, false if the player is stopped
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Change moving boolean
     * @param moving boolean to which the moving boolean will be changed
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Mark the player as stunned
     */
    public void stun() {
        stunned = true;
    }

    /**
     * Unmark the player as stunned
     */
    public void  unstun() {
        stunned = false;
    }

    /**
     * Returns whether the player is stunned or not
     * @return True if the player is stunned, false if not
     */
    public boolean isStunned() {
        return stunned;
    }

    /**
     * The enum with the different Orientations of the player
     */
    public enum Orientation {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    /**
     * Returns whether the player's death process has started or not
     * @return True if the death process is on going
     */
    public boolean isDying(){
        return dying;
    }

    /**
     * Start the player's death process
     */
    public void startDying(){
        dying = true;
    }

    /**
     * Decreases the time left until the player's removal
     * @param delta The size of this physics step in seconds.
     * @return True if the time is below 0
     */
    public boolean decreaseTimeTillDeath(float delta) {
        time_till_death -= delta;
        return time_till_death < 0;
    }

    /**
     * Gets the current orientation of the Player
     * @return orientation of the player
     */
    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    /**
     * Sets the player orientation
     * @param currentOrientation the orientation of the player
     */
    public void setOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    /**
     * Gets the player's bombs explosion radius based on the power ups caught
     * @return The amount of bricks the explosion will propagate
     */
    public int getExplosionRadius() {
        return STARTING_EXPLOSION_RADIUS + explosionChanger;
    }

    /**
     * Increases speed changer if it is not already too high
     */
    public void speedUp() {
        if (speedChanger < MAX_SPEED_BONUS) {
            speedChanger++;
        }
    }

    /**
     * Decreases speed changer if it is not already too low
     */
    public void speedDown() {
        if (speedChanger > MAX_SPEED_PENALTY) {
            speedChanger--;
        }
    }

    /**
     * Increases the radius changer if it is not already too high
     */
    public void radiusUp() {
        if(explosionChanger < MAX_EXPLODING_BONUS){
            explosionChanger++;
        }
    }

    /**
     * Decreases the radius changer if it is not already too low
     */
    public void radiusDown() {
        if (explosionChanger > MAX_EXPLODING_PENALTY) {
            explosionChanger--;
        }
    }

    /**
     * Increases the bombs allowed changer if it is not already too high
     */
    public void increaseAllowedBombs() {
        if(allowedBombsChanger < MAX_BOMBS_BONUS){
            allowedBombsChanger++;
        }
    }

    /**
     * Decreases the bombs allowed changer if it is not already too low
     */
    public void decreaseAllowedBombs() {
        if(allowedBombsChanger > MAX_BOMBS_PENALTY){
            allowedBombsChanger--;
        }
    }

    /**
     * Increments the bombs that are active
     * @return True if it is possible to increment it, false if not
     */
    public boolean incrementActiveBombs() {
        if(activeBombs < STARTING_ALLOWED_BOMBS + allowedBombsChanger) {
            activeBombs++;
            return true;
        }
        return false;
    }

    /**
     * Decrements the active bombs
     */
    public void decrementActiveBombs() {
        activeBombs--;
    }

    /**
     * Gets the amount of speed power ups caught
     * @return Amount of speed power ups
     */
    public int getSpeedChanger() {
        return speedChanger;
    }

    /**
     * Gets the amount of explosion power ups caught
     * @return Amount of explosion power ups
     */
    public int getExplosionChanger() {
        return explosionChanger;
    }

    /**
     * Gets the amount of bombs power ups caught
     * @return Amount of bombs power ups
     */
    public int getAllowedBombsChanger() {
        return allowedBombsChanger;
    }

    /**
     * Returns whether the player is above a bomb
     * @return True if the player is above a bomb
     */
    public boolean isOverBomb() {
        return overBomb;
    }

    /**
     * Sets whether the player is over a bomb or not
     * @param overBomb True if the player is above a bomb
     */
    public void setOverBomb(boolean overBomb) {
        this.overBomb = overBomb;
    }

    /**
     * Gets the id of the player
     * @return id of the player
     */
    public int getId() {
        return id;
    }
}
