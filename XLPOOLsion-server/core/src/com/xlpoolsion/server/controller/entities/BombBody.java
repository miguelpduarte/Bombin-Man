package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.BombModel;

public class BombBody extends EntityBody {
    public BombBody(World world, BombModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 8.0f;
        float restitution = 5.0f;

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
