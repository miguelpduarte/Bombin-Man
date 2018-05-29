package com.xlpoolsion.client.controller;

public class GameController {
    private static GameController instance = null;

    public void waitForServer() {
        currentState = STATE.WAITING_FOR_SERVER;
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
