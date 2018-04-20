package com.xlpoolsion.server.model;

public class GameModel {
    private static GameModel instance = null;
    private PlayerModel player;

    private GameModel() {
        player = new PlayerModel();
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

    public void update(float delta) {
        //System.out.println("I am Game Model and am updating with delta " + delta);
    }
}
