package com.xlpoolsion.client.controller;

/**
 * Controls the states of the client
 */
public class GameController {
    private static GameController instance = null;

    /**
     * Sets the state to one where the client waits for the start of the game
     */
    public void waitForServer() {
        currentState = STATE.WAITING_FOR_SERVER;
    }

    /**
     * Sets the state to playing
     */
    public void startGame() {
        currentState = STATE.PLAYING;
    }

    /**
     * Sets the state to server full
     */
    public void signalServerFull() {
        currentState = STATE.SERVER_FULL;
    }

    /**
     * Resets the states
     */
    public void resetState() {
        currentState = STATE.NOT_CONNECTED;
        //Just to make sure
        isStunned = false;
    }

    /**
     * Sets the state to Victory
     */
    public void signalWonGame() {
        currentState = STATE.WON;
    }

    /**
     * Sets the state to Defeat
     */
    public void signalLostGame() {
        currentState = STATE.LOST;
    }

    /**
     * Sets the state to Lost connection
     */
    public void signalLostConnection() {
        currentState = STATE.LOST_CONNECTION;
    }

    private boolean isStunned = false;

    public void signalStunned() {
        isStunned = true;
    }

    /**
     * Signals the end of stun
     */
    public void signalUnstunned() {
        isStunned = false;
    }

    /**
     * Returns stun boolean
     * @return true if the client is stunned
     */
    public boolean isStunned() {
        return isStunned;
    }

    /**
     * State defining the different screens to be shown
     */
    public enum STATE {
        NOT_CONNECTED, WAITING_FOR_SERVER, PLAYING, SERVER_FULL, LOST_CONNECTION, WON, LOST
    }

    private STATE currentState;

    /**
     * Gets the current state
     * @return current state
     */
    public STATE getCurrentState() {
        return currentState;
    }

    private GameController() {
        currentState = STATE.NOT_CONNECTED;
    }

    /**
     * Returns the current Game Controller instance
     * @return the game controller instance
     */
    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }
}
