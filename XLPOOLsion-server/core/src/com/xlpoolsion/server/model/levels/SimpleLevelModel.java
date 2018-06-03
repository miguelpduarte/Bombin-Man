package com.xlpoolsion.server.model.levels;

import com.badlogic.gdx.math.Vector2;

/**
 * A concrete representation of the BaseLevelModel used to change the bricks that define the level
 */
public class SimpleLevelModel extends BaseLevelModel {
    public SimpleLevelModel(boolean[] connectedPlayers) {
        super(connectedPlayers, new Vector2[] {
                new Vector2(GRID_START_X_BRICKS + 1, GRID_END_Y_BRICKS - 1), new Vector2(GRID_END_X_BRICKS - 2, GRID_END_Y_BRICKS - 1), new Vector2(GRID_START_X_BRICKS + 1, GRID_START_Y_BRICKS + 1), new Vector2(GRID_END_X_BRICKS - 2, GRID_START_Y_BRICKS + 1)
        });
    }

    @Override
    protected void createBreakableBricks() {
        for(int i = GRID_START_Y_BRICKS + 1; i < GRID_END_Y_BRICKS - 1; ++i) {
            for(int j = GRID_START_X_BRICKS + 1; j < GRID_END_X_BRICKS - 1; ++j) {
                if(i == GRID_START_Y_BRICKS + 1 || i == GRID_END_Y_BRICKS - 2) {
                    //Top and bottom lines
                    if(j > GRID_START_X_BRICKS + 2 && j < GRID_END_X_BRICKS - 3) {
                        createBreakableBrick(j, i);
                    }
                } else if(i == GRID_START_Y_BRICKS + 2 || i == GRID_END_Y_BRICKS - 3) {
                    //Top-1 and bottom+1 lines
                    if(j % 2 != 0 && j != GRID_START_X_BRICKS + 1 && j != GRID_END_X_BRICKS - 2) {
                        createBreakableBrick(j, i);
                    }
                } else {
                    if(i % 2 == 0) {
                        //Line with solid bricks
                        if(j % 2 != 0) {
                            createBreakableBrick(j, i);
                        }
                    } else {
                        //Line of full breakable bricks
                        createBreakableBrick(j, i);
                    }
                }
            }
        }
    }

    @Override
    protected void createBricks() {
        //Outer bounds
        for(int i = GRID_START_Y_BRICKS; i < GRID_END_Y_BRICKS; ++i) {
            for(int j = GRID_START_X_BRICKS; j < GRID_END_X_BRICKS; ++j) {
                if(i*j == 0 || i == GRID_END_Y_BRICKS - 1 || j == GRID_END_X_BRICKS - 1) {
                    createBrick(j, i);
                }
            }
        }

        //Inside crossed
        for(int i = GRID_START_Y_BRICKS + 1; i < GRID_END_Y_BRICKS - 1; ++i) {
            for(int j = GRID_START_X_BRICKS + 1; j < GRID_END_X_BRICKS - 1; ++j) {
                if(i % 2 == 0 && j % 2 == 0) {
                    createBrick(j, i);
                }
            }
        }
    }
}
