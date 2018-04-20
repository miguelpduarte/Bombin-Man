package com.xlpoolsion.server.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.PlayerModel;

public class PlayerBody {
    /**
     * The Box2D body that supports this body.
     */
    final Body body;


    public PlayerBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        //Switch to get rotation? Maybe when there is an EntityModel and EntityBody
        bodyDef.angle = 0;

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    public void moveUp() {
        body.setLinearVelocity(0, ((PlayerModel)body.getUserData()).getCurrentSpeed());
    }

    public void moveDown() {
        body.setLinearVelocity(0, -((PlayerModel)body.getUserData()).getCurrentSpeed());
    }

    public void moveLeft() {
        body.setLinearVelocity(- ((PlayerModel)body.getUserData()).getCurrentSpeed(), 0);
    }

    public void moveRight() {
        body.setLinearVelocity(((PlayerModel)body.getUserData()).getCurrentSpeed(), 0);
    }

    public void stop() {
        body.setLinearVelocity(0, 0);
    }

    public Object getUserData() {
        return body.getUserData();
    }
}
