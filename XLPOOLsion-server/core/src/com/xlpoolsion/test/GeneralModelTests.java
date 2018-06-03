package com.xlpoolsion.test;

import com.xlpoolsion.server.model.entities.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GeneralModelTests {
    @Test
    public void bombTest() {
        BombModel bombModel = new BombModel(1, 1, 0);
        PlayerModel bombOwner = new PlayerModel(0, 0, 0, 0);
        bombModel.setOwner(bombOwner);
        assertEquals(bombOwner, bombModel.getOwner());
        assertEquals(1, bombModel.getX(), 0.0001f);
        assertEquals(1, bombModel.getY(), 0.0001f);
    }

    @Test
    public void explosionTest() {
        ExplosionModel explosionModel = new ExplosionModel(0, 0, 0);
        assertEquals(1f, explosionModel.getTimeToDecay());
        explosionModel.setTimeToDecay(2f);
        assertEquals(2f, explosionModel.getTimeToDecay());
        explosionModel.decreaseTimeToDecay(2f);
        assertEquals(0f, explosionModel.getTimeToDecay());
    }

    @Test
    public void brickTest() {
        BrickModel brickModel = new BrickModel(0, 5, 0);
        assertEquals(0, brickModel.getX(), 0.0001f);
        assertEquals(5, brickModel.getY(), 0.0001f);
        assertEquals(0, brickModel.getRotation(), 0.0001f);
        assertFalse(brickModel.isFlaggedForRemoval());
        brickModel.setFlaggedForRemoval(true);
        assertTrue(brickModel.isFlaggedForRemoval());
    }

    @Test
    public void breakableBrickTest() {
        BreakableBrickModel breakableBrickModel = new BreakableBrickModel(6, 2.5f, 1.1f);
        assertFalse(breakableBrickModel.isFlaggedForRemoval());
        breakableBrickModel.setFlaggedForRemoval(true);
        assertTrue(breakableBrickModel.isFlaggedForRemoval());
        assertEquals(6, breakableBrickModel.getX(), 0.0001f);
    }
}
