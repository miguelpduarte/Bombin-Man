package com.xlpoolsion.server.model;

import com.xlpoolsion.server.controller.GameController;

public class GameModel {
    private static GameModel instance = null;

    private GameModel() {}


    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }
}
