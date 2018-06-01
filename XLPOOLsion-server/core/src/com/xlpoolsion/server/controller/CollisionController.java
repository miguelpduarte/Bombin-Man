package com.xlpoolsion.server.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.controller.entities.PlayerBody;
import com.xlpoolsion.server.model.entities.*;

public class CollisionController implements ContactListener {
    private static CollisionController instance = null;

    private CollisionController() {
    }

    public static CollisionController getInstance() {
        if(instance == null) {
            instance = new CollisionController();
        }

        return instance;
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof ExplosionModel) {
            playerExplosionContact(bodyA, bodyB);
        } else if(bodyA.getUserData() instanceof  ExplosionModel && bodyB.getUserData() instanceof PlayerModel) {
            playerExplosionContact(bodyB, bodyA);
        }

        if(bodyA.getUserData() instanceof BreakableBrickModel && bodyB.getUserData() instanceof ExplosionModel) {
            breakableBrickExplosionContact(bodyA, bodyB);
        } else if(bodyA.getUserData() instanceof ExplosionModel && bodyB.getUserData() instanceof BreakableBrickModel) {
            breakableBrickExplosionContact(bodyB, bodyA);
        }

        if(bodyA.getUserData() instanceof PowerUpModel && bodyB.getUserData() instanceof PlayerModel) {
            powerUpContact(bodyA,bodyB);
        } else if(bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof PowerUpModel) {
            powerUpContact(bodyB, bodyA);
        }

        if(bodyA.getUserData() instanceof PowerDownModel && bodyB.getUserData() instanceof PlayerModel) {
            powerDownContact(bodyA,bodyB);
        } else if(bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof PowerDownModel) {
            powerDownContact(bodyB, bodyA);
        }

        if(bodyA.getUserData() instanceof StunPowerModel && bodyB.getUserData() instanceof PlayerModel) {
            stunPowerContact(bodyA, bodyB);
        } else if(bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof StunPowerModel) {
            stunPowerContact(bodyB, bodyA);
        }
    }

    private void stunPowerContact(Body stunPowerBody, Body playerBody) {
        GameController.getInstance().playerStunnedOtherPlayers((PlayerModel) playerBody.getUserData());
        ((StunPowerModel) stunPowerBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void breakableBrickExplosionContact(Body breakableBrickBody, Body explosionBody) {
        ((BreakableBrickModel) breakableBrickBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void powerUpContact(Body powerUpBody,Body playerBody){
        switch (((PowerUpModel)powerUpBody.getUserData()).getType()){
            case SpeedUp:
                ((PlayerModel) playerBody.getUserData()).speedUp();
                break;
            case BombRadUp:
                ((PlayerModel) playerBody.getUserData()).radiusUp();
                break;
            case BombsUp:
                ((PlayerModel) playerBody.getUserData()).increaseAllowedBombs();
                break;
            case RandomUp:
                addRandomBonus(playerBody);
                break;
        }
        ((PowerUpModel)powerUpBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void addRandomBonus(Body playerBody) {
        switch ((int) Math.random() * 3) {
            case 0:
                ((PlayerModel) playerBody.getUserData()).speedUp();
                break;
            case 1:
                ((PlayerModel) playerBody.getUserData()).radiusUp();
                break;
            case 2:
                ((PlayerModel) playerBody.getUserData()).increaseAllowedBombs();
                break;

        }
    }

    private void powerDownContact(Body powerDownBody,Body playerBody){
        switch(((PowerDownModel)powerDownBody.getUserData()).getType()){
            case SpeedDown:
                ((PlayerModel) playerBody.getUserData()).speedDown();
                break;
            case BombsDown:
                ((PlayerModel) playerBody.getUserData()).decreaseAllowedBombs();
                break;
            case BombRadDown:
                ((PlayerModel) playerBody.getUserData()).radiusDown();
                break;
            case RandomDown:
                addRandomPenalty(playerBody);
                break;
        }
        ((PowerDownModel)powerDownBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void addRandomPenalty(Body playerBody) {
        switch ((int) Math.random() * 3) {
            case 0:
                ((PlayerModel) playerBody.getUserData()).speedDown();
                break;
            case 1:
                ((PlayerModel) playerBody.getUserData()).radiusDown();
                break;
            case 2:
                ((PlayerModel) playerBody.getUserData()).decreaseAllowedBombs();
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof BombModel && ((BombModel)bodyB.getUserData()).isWalkable()) {
            enableBombCollisions(bodyB);
            setNotOverBomb(bodyA);
        } else if (bodyB.getUserData() instanceof PlayerModel && bodyA.getUserData() instanceof  BombModel && ((BombModel)bodyA.getUserData()).isWalkable()) {
            enableBombCollisions(bodyA);
            setNotOverBomb(bodyB);
        }
    }

    private void setNotOverBomb(Body playerBody) {
        ((PlayerModel) playerBody.getUserData()).setOverBomb(false);
    }

    /**
     * Enables collisions of a bomb bombBody
     * @param bombBody Body of bomb to enable collisions for
     */
    private void enableBombCollisions(Body bombBody) {
        ((BombModel)bombBody.getUserData()).setWalkable(false);

        Array<Fixture> fixtures = bombBody.getFixtureList();
        for(Fixture fixture : fixtures) {
            fixture.setSensor(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void playerExplosionContact(Body playerBody, Body explosionBody) {
        GameController.getInstance().killPlayer((PlayerModel) playerBody.getUserData());
    }
}
