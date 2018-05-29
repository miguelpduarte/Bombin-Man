package com.xlpoolsion.client.controller;

public class GameController {
    private static GameController instance = null;

    public void waitForServer() {
        currentState = STATE.WAITING_FOR_SERVER;
    }

    public void startGame() {
        currentState = STATE.PLAYING;
    }

    public void signalServerFull() {
        currentState = STATE.SERVER_FULL;
    }

    ///Use in back button to reset controller state
    public void resetState() {
        currentState = STATE.NOT_CONNECTED;
    }

    public void signalWonGame() {
        currentState = STATE.WON;
    }

    public void signalLostGame(int i) {
        currentState = STATE.LOST;
    }

    public void signalLostConnection() {
        currentState = STATE.LOST_CONNECTION;
    }

    public enum STATE {
        NOT_CONNECTED, WAITING_FOR_SERVER, PLAYING, SERVER_FULL, LOST_CONNECTION, WON, LOST
    }

    private STATE currentState;

    public STATE getCurrentState() {
        return currentState;
    }

    private GameController() {
        currentState = STATE.NOT_CONNECTED;
    }

    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }
}
