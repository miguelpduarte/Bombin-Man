package com.xlpoolsion.test;

import com.xlpoolsion.server.model.entities.PowerDownModel;
import com.xlpoolsion.server.model.entities.PowerUpModel;
import org.junit.Test;

public class PowersModelTests {
    //To verify if it is created one power up of every type eventually
    @Test(timeout = 1000)
    public void testRandomPowerUp(){
        boolean[] allPowers = new boolean[4];
        while(!allTrue(allPowers)){
            PowerUpModel tempPower = new PowerUpModel(0,0,0);
            switch (tempPower.getType()){
                case RandomUp:
                    allPowers[0] = true;
                    break;
                case BombRadUp:
                    allPowers[1] = true;
                    break;
                case SpeedUp:
                    allPowers[2] = true;
                    break;
                case BombsUp:
                    allPowers[3] = true;
                    break;
            }

        }
    }
    //To verify if it is created one power down of every type eventually
    @Test(timeout = 1000)
    public void testRandomPowerDown(){
        boolean[] allPowers = new boolean[4];
        while(!allTrue(allPowers)){
            PowerDownModel tempPower = new PowerDownModel(0,0,0);
            switch (tempPower.getType()){
                case RandomDown:
                    allPowers[0] = true;
                    break;
                case BombRadDown:
                    allPowers[1] = true;
                    break;
                case SpeedDown:
                    allPowers[2] = true;
                    break;
                case BombsDown:
                    allPowers[3] = true;
                    break;
            }

        }
    }

    private boolean allTrue(boolean[] bArr) {
        for (boolean b : bArr) {
            if (b == false) {
                return false;
            }
        }
        return true;
    }
}
