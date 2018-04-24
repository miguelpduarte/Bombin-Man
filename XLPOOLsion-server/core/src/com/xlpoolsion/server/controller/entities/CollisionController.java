package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

public class CollisionController implements ContactListener {
    private static CollisionController instance = null;

    private static final float PUSH_SPEED = 80f;

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

        if(bodyA.getUserData() instanceof BombModel && !((BombModel) bodyA.getUserData()).isWalkable()
                && bodyB.getUserData() instanceof PlayerModel) {
            if(((PlayerModel) bodyB.getUserData()).hasKickPowerup()) {
                pushBomb(bodyB, bodyA);
            } else {
                stopBomb(bodyA);
            }

        }

        if (bodyB.getUserData() instanceof BombModel && !((BombModel) bodyB.getUserData()).isWalkable()
                && bodyA.getUserData() instanceof PlayerModel){
            if(((PlayerModel) bodyA.getUserData()).hasKickPowerup()) {
                pushBomb(bodyA, bodyB);
            } else {
                stopBomb(bodyB);
            }
        }
    }

    private void stopBomb(Body bombBody) {
        bombBody.setLinearVelocity(0f, 0f);
    }

    private void pushBomb(Body playerBody, Body bombBody) {
        float dx = playerBody.getPosition().x - bombBody.getPosition().x;
        float dy = playerBody.getPosition().y - bombBody.getPosition().y;

        if(Math.abs(dx) > Math.abs(dy)) {
            //x verifications
            if(dx > 0) {
                //Player is right of Bomb
                bombBody.setLinearVelocity(-PUSH_SPEED, 0f);
            } else {
                //Player is left of Bomb
                bombBody.setLinearVelocity(PUSH_SPEED, 0f);
            }
        } else {
            //y verifications
            if(dy > 0) {
                //Player is above Bomb
                bombBody.setLinearVelocity(0f, -PUSH_SPEED);
            } else {
                //Player is below Bomb
                bombBody.setLinearVelocity(0f, PUSH_SPEED);
            }
        }
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
     * Enables collisions of a bomb body
     * @param body Body of bomb to enable collisions for
     */
    private void enableBombCollisions(Body body) {
        ((BombModel)body.getUserData()).setWalkable(false);
        /*
        Array<Fixture> fixtures = body.getFixtureList();
        for(Fixture fixture : fixtures) {
            fixture.setSensor(false);
        }
        */
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
