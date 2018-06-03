package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.EntityModel;

/**
 * Wrapper class that represents an abstract physical
 * body supported by a Box2D body.
 */
public class EntityBody {

    /**
     * The Box2D body that supports this body.
     */
    final Body body;

    EntityBody(World world, EntityModel model, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(), model.getY());
        bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    public Object getUserData() {
        return body.getUserData();
    }
}