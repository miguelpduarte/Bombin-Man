package com.xlpoolsion.server.controller;

import com.badlogic.gdx.math.Vector2;
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
        //Body bodyA = contact.getFixtureA().getBody();
        //Body bodyB = contact.getFixtureB().getBody();
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
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof PlayerModel && bodyB.getUserData() instanceof BombModel && !((PlayerModel) bodyA.getUserData()).hasKickPowerup()) {
            contact.setEnabled(false);
            stopPlayerIfMovingTowardsBomb(bodyA, bodyB);
        }

        if(bodyB.getUserData() instanceof PlayerModel && bodyA.getUserData() instanceof BombModel && !((PlayerModel) bodyB.getUserData()).hasKickPowerup()) {
            contact.setEnabled(false);
            stopPlayerIfMovingTowardsBomb(bodyB, bodyA);
        }
    }

    /**
     * Stops a player (in the correct velocity component) if he is moving towards a bomb. For use when the player collides with a bomb but does not have the kick pickup.
     * @param playerBody
     * @param bombBody
     */
    private void stopPlayerIfMovingTowardsBomb(Body playerBody, Body bombBody) {
        Vector2 playerVel = playerBody.getLinearVelocity();

        if(playerVel.isZero()) {
            return;
        }

        Vector2 playerToBomb = bombBody.getPosition().sub(playerBody.getPosition());

        System.out.println("Player: " + playerVel + "\nPlayerPos: " + playerBody.getPosition() + "\nBombPos: " + bombBody.getPosition() + "\nDiff: " + playerToBomb);

        if(playerVel.y == 0) {
            //Check collisions in X
            System.out.println("Testing in X");
            if(playerToBomb.x * playerVel.x > 0) {
                playerBody.setLinearVelocity(0, playerBody.getLinearVelocity().y);
                System.out.println("Ocurred");
            }
        } else if(playerVel.x == 0) {
            //Check collisions in Y (second condition to not check when not moving)
            System.out.println("Testing in Y");
            if(playerToBomb.y * playerVel.y > 0) {
                playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
                System.out.println("Ocurred");
            }
        }

        /*
        Vector2 playerToBomb = bombBody.getPosition().sub(playerBody.getPosition());
        //Converting to "directional" vector -> only largest direction in absolute matters
        if(Math.abs(playerToBomb.x) > Math.abs(playerToBomb.y)) {
            playerToBomb.y = 0;
        } else {
            playerToBomb.x = 0;
        }


        //System.out.println(new Vector2(0f, 4f).isCollinearOpposite(new Vector2(0, -2.19734f)));
        System.out.println("pVel: " + playerVel);
        System.out.println("pToBomb: " + playerToBomb);

        if(playerVel.isCollinear(playerToBomb)) {
            playerBody.setLinearVelocity(0, 0);
        } else {
            System.out.println("Did not stop");
        }
        */
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
