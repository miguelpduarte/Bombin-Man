package com.xlpoolsion.test;

import com.xlpoolsion.server.model.entities.PlayerModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerModelTests {
    @Test
    public void playerModelValuesTest() {
        PlayerModel playerModel = new PlayerModel(1, 2, 0, 0);
        assertEquals(0, playerModel.getId());

        //Position
        assertEquals(1, playerModel.getX(), 0);
        assertEquals(2, playerModel.getY(), 0);
        playerModel.setPosition(6, 5);
        assertEquals(6, playerModel.getX(), 0);
        assertEquals(5, playerModel.getY(), 0);

        ////STATE BOOLEANS

        assertFalse(playerModel.isDying());
        playerModel.startDying();
        assertTrue(playerModel.isDying());
        playerModel.startDying();
        assertTrue(playerModel.isDying());

        assertFalse(playerModel.isStunned());
        playerModel.stun();
        assertTrue(playerModel.isStunned());
        playerModel.unstun();
        assertFalse(playerModel.isStunned());

        assertFalse(playerModel.isMoving());
        playerModel.setMoving(true);
        assertTrue(playerModel.isMoving());
        playerModel.setMoving(false);
        assertFalse(playerModel.isMoving());

        assertFalse(playerModel.isOverBomb());
        playerModel.setOverBomb(true);
        assertTrue(playerModel.isOverBomb());
        playerModel.setOverBomb(false);
        assertFalse(playerModel.isOverBomb());

        assertFalse(playerModel.isFlaggedForRemoval());
        playerModel.setFlaggedForRemoval(true);
        assertTrue(playerModel.isFlaggedForRemoval());
    }

    @Test
    public void playerModelExplosionRadiusTest() {
        PlayerModel playerModel = new PlayerModel(0, 0, 0, 0);

        //Initial conditions
        assertEquals(3, playerModel.getExplosionRadius());
        assertEquals(0, playerModel.getExplosionChanger());
        playerModel.radiusUp();
        assertEquals(4, playerModel.getExplosionRadius());
        assertEquals(1, playerModel.getExplosionChanger());
        playerModel.radiusDown();
        assertEquals(3, playerModel.getExplosionRadius());
        assertEquals(0, playerModel.getExplosionChanger());
        playerModel.radiusDown();
        playerModel.radiusDown();
        assertEquals(1, playerModel.getExplosionRadius());
        assertEquals(-2, playerModel.getExplosionChanger());
        //Testing minimum
        playerModel.radiusDown();
        assertEquals(1, playerModel.getExplosionRadius());
        assertEquals(-2, playerModel.getExplosionChanger());

        PlayerModel largeRangePlayer = new PlayerModel(0, 0, 0, 0);

        //Initial conditions
        assertEquals(3, largeRangePlayer.getExplosionRadius());
        assertEquals(0, largeRangePlayer.getExplosionChanger());

        //Testing maximum
        for(int i = 0; i < 6; ++i) {
            largeRangePlayer.radiusUp();
        }

        assertEquals(9, largeRangePlayer.getExplosionRadius());
        assertEquals(6, largeRangePlayer.getExplosionChanger());
        largeRangePlayer.radiusUp();
        largeRangePlayer.radiusUp();
        largeRangePlayer.radiusUp();
        assertEquals(9, largeRangePlayer.getExplosionRadius());
        assertEquals(6, largeRangePlayer.getExplosionChanger());
    }

    @Test
    public void playerModelSpeedTest() {
        PlayerModel playerModel = new PlayerModel(1, 2, 0, 0);

        //Initial conditions
        assertEquals(6f, playerModel.getCurrentSpeed(), 0.0001f);
        assertEquals(0, playerModel.getSpeedChanger());

        //Testing minimum
        playerModel.speedDown();
        playerModel.speedDown();
        assertEquals(6f - 2*0.6f, playerModel.getCurrentSpeed(), 0.0001f);
        assertEquals(-2, playerModel.getSpeedChanger());
        playerModel.speedDown();
        playerModel.speedDown();
        playerModel.speedDown();
        assertEquals(6f - 2*0.6, playerModel.getCurrentSpeed(), 0.0001f);
        assertEquals(-2, playerModel.getSpeedChanger());

        PlayerModel fastPlayer = new PlayerModel(0, 0, 0, 0);
        //Testing maximum

        //Initial conditions
        assertEquals(6f, fastPlayer.getCurrentSpeed(), 0.0001f);
        assertEquals(0, fastPlayer.getSpeedChanger());

        for(int i = 0; i < 6; ++i) {
            fastPlayer.speedUp();
        }

        assertEquals(6f + 6*0.6f, fastPlayer.getCurrentSpeed(), 0.0001f);
        assertEquals(6, fastPlayer.getSpeedChanger());
        fastPlayer.speedUp();
        fastPlayer.speedUp();
        assertEquals(6f + 6*0.6f, fastPlayer.getCurrentSpeed(), 0.0001f);
        assertEquals(6, fastPlayer.getSpeedChanger());
    }

    @Test
    public void playerNBombsPlacedPowerupTest() {
        PlayerModel playerModel = new PlayerModel(0, 0, 0, 0);

        //Initial conditions
        assertEquals(0, playerModel.getAllowedBombsChanger());
        //Testing minimum
        playerModel.decreaseAllowedBombs();
        assertEquals(0, playerModel.getAllowedBombsChanger());

        //Testing maximum
        for(int i = 0; i < 10; ++i) {
            playerModel.increaseAllowedBombs();
        }
        assertEquals(10, playerModel.getAllowedBombsChanger());
        playerModel.increaseAllowedBombs();
        playerModel.increaseAllowedBombs();
        playerModel.increaseAllowedBombs();
        assertEquals(10, playerModel.getAllowedBombsChanger());
    }
}
