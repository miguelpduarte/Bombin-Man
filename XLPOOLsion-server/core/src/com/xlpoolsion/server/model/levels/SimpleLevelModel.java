package com.xlpoolsion.server.model.levels;

import com.badlogic.gdx.math.Vector2;

public class SimpleLevelModel extends BaseLevelModel {
    public SimpleLevelModel(boolean[] connectedPlayers) {
        super(connectedPlayers, new Vector2[] {
                new Vector2(20, 20), new Vector2(32, 35), new Vector2(12, 0), new Vector2(0, 12)
        });
    }

    @Override
    protected void createBreakableBricks() {
        for(int i = 0; GRID_START_Y + i*GRID_PADDING_Y < GRID_END_Y; ++i) {
            for(int j = 0; GRID_START_X + j*GRID_PADDING_X < GRID_END_X; ++j) {
                if (i % 4 == 0) {
                    //Linha em que tem fixos
                    //Desenhar a nao por um a cada 4, começando a nao por
                    if(j % 6 != 0) {
                        createBreakableBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                    }
                } else {
                    //Linha sem fixos, desenhar espaçado
                    if(j % 6 == 0) {
                        createBreakableBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                    }
                }
            }
        }
    }

    @Override
    protected void createBricks() {
        for(int i = 0; GRID_START_Y + i*GRID_PADDING_Y < GRID_END_Y; i++) {
            for(int j = 0; GRID_START_X + j*GRID_PADDING_X < GRID_END_X; j++) {
                if(i*j == 0 || GRID_START_X *2  + i * GRID_PADDING_Y > GRID_END_Y || GRID_START_X * 2 + j * GRID_PADDING_X > GRID_END_X){
                    createBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
                }

            }
        }

        for(int i = 0; GRID_START_Y + i*GRID_PADDING_Y < GRID_END_Y; i += 4) {
            for(int j = 0; GRID_START_X + j*GRID_PADDING_X < GRID_END_X; j += 6) {
                createBrick(GRID_START_X + j*GRID_PADDING_X, GRID_START_Y + i*GRID_PADDING_Y);
            }
        }
    }
}
