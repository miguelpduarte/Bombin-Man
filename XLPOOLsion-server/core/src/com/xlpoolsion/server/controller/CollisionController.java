package com.xlpoolsion.server.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
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
    }

    private void breakableBrickExplosionContact(Body breakableBrickBody, Body explosionBody) {
        ((BreakableBrickModel) breakableBrickBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void powerUpContact(Body powerUpBody,Body playerBody){
        if(((PowerUpModel)powerUpBody.getUserData()).getType() == PowerUpModel.PowerUpType.SpeedUp) {
            ((PlayerModel) playerBody.getUserData()).speedUp();
        } /*else if(((PowerUpModel)powerUpBody.getUserData()).getType() == PowerUpModel.PowerUpType.SpeedDown) {
            ((PlayerModel) playerBody.getUserData()).speedDown();
        }*/ else if(((PowerUpModel)powerUpBody.getUserData()).getType() == PowerUpModel.PowerUpType.BombRadUp) {
            ((PlayerModel) playerBody.getUserData()).radiusUp();
        }/* else if(((PowerUpModel)powerUpBody.getUserData()).getType() == PowerUpModel.PowerUpType.BombRadDown) {
            ((PlayerModel) playerBody.getUserData()).radiusDown();
        }*/
        ((PowerUpModel)powerUpBody.getUserData()).setFlaggedForRemoval(true);
    }

    @Override
    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof BombModel && ((BombModel)bodyB.getUserData()).isWalkable()) {
            enableBombCollisions(bodyB);
        } else if (bodyB.getUserData() instanceof PlayerModel && bodyA.getUserData() instanceof  BombModel && ((BombModel)bodyA.getUserData()).isWalkable()) {
            enableBombCollisions(bodyA);
        }
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
        System.out.println("Player ded");
    }
}
