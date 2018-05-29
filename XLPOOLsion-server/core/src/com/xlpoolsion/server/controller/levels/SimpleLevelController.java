package com.xlpoolsion.server.controller.levels;

import com.xlpoolsion.server.model.levels.SimpleLevelModel;

public class SimpleLevelController extends BaseLevelController {
    public SimpleLevelController(boolean[] connectedPlayers) {
        super(connectedPlayers, new SimpleLevelModel(connectedPlayers));
    }
}
