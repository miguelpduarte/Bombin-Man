package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.xlpoolsion.server.model.entities.BombModel;

public class BombBody extends EntityBody {

    public BombBody(World world, BombModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 8.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(BombModel.WIDTH / 2, BombModel.HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        polyShape.dispose();
    }
}
