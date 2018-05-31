package com.xlpoolsion.server.model.levels;

import com.badlogic.gdx.math.Vector2;

import static com.xlpoolsion.server.controller.levels.BaseLevelController.*;

public class SimpleLevelModel extends BaseLevelModel {
    public SimpleLevelModel(boolean[] connectedPlayers) {
        super(connectedPlayers, new Vector2[] {
                new Vector2(20, 20), new Vector2(32, 35), new Vector2(12, 0), new Vector2(0, 12)
        });
    }

    @Override
    protected void createBreakableBricks() {
        for(int i = GRID_START_Y_BRICKS + 1; i < GRID_END_Y_BRICKS - 1; ++i) {
            for(int j = GRID_START_X_BRICKS + 1; j < GRID_END_X_BRICKS - 1; ++j) {
                if (i % 4 == 0) {
                    //Linha em que tem fixos
                    //Desenhar a nao por um a cada 4, começando a nao por
                    if(j % 6 != 0) {
                        createBreakableBrick(j, i);
                    }
                } else {
                    //Linha sem fixos, desenhar espaçado
                    if(j % 6 == 0) {
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

        /*
        for(int i = GRID_START_Y_BRICKS + 1; i < GRID_END_Y_BRICKS - 1; i += 4) {
            for(int j = GRID_START_X_BRICKS + 1; j < GRID_END_X_BRICKS - 1; j += 6) {
                createBrick(j, i);
            }
        }
        */

        //Debugging screen center
        createBrick(LEVEL_WIDTH_BRICKS/2, LEVEL_HEIGHT_BRICKS/2);
    }
}
