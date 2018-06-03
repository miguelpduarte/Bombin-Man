package com.xlpoolsion.server.controller.levels;

import com.xlpoolsion.server.model.levels.SimpleLevelModel;

/**
 * A concrete representation of a BaseLevelController
 */
public class SimpleLevelController extends BaseLevelController {
    public SimpleLevelController(boolean[] connectedPlayers) {
        super(connectedPlayers, new SimpleLevelModel(connectedPlayers));
    }
}
