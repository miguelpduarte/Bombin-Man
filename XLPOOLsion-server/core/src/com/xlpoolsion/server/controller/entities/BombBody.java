package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.xlpoolsion.server.model.entities.BombModel;

public class BombBody extends EntityBody {
    private Fixture mainFixture;

    public BombBody(World world, BombModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 8.0f;
        float restitution = 0.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(BombModel.WIDTH / 2, BombModel.HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = true;

        mainFixture = body.createFixture(fixtureDef);

        polyShape.dispose();
    }

    /**
     * Toggles the main fixture between being a sensor or not, useful for avoiding collisions until the player walks off
     * @param isSensor If the main fixture is a sensor or not
     */
    public void setSensor(boolean isSensor) {
        mainFixture.setSensor(isSensor);
    }
}