package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

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
        /*
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        */
    }

    @Override
    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof BombModel && ((BombModel)bodyB.getUserData()).isWalkable()) {
            enableBombCollisions(bodyB);
        }

        if (bodyB.getUserData() instanceof PlayerModel && bodyA.getUserData() instanceof  BombModel && ((BombModel)bodyA.getUserData()).isWalkable()) {
            enableBombCollisions(bodyA);
        }
    }

    /**
     * Enables collisions of a bomb body
     * @param body Body of bomb to enable collisions for
     */
    private void enableBombCollisions(Body body) {
        ((BombModel)body.getUserData()).setWalkable(false);
        Array<Fixture> fixtures = body.getFixtureList();
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
}
