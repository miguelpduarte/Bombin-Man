package com.xlpoolsion.server.controller.levels;

import com.xlpoolsion.server.model.levels.SimpleLevelModel;

/**
 * A concrete representation of a BaseLevelController used to build different types of levels
 */
public class SimpleLevelController extends BaseLevelController {
    public SimpleLevelController(boolean[] connectedPlayers) {
        super(connectedPlayers, new SimpleLevelModel(connectedPlayers));
    }
}
