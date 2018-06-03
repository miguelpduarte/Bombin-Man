package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.xlpoolsion.server.model.entities.BombModel;

/**
 *  A concrete representation of an EntityBody representing a bomb.
 */
public class BombBody extends EntityBody {
    /**
     * Constructs a Bomb body according to
     * a Bomb model.
     *
     * @param world the physical world this bomb belongs to.
     * @param model the model representing this bomb.
     */
    public BombBody(World world, BombModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 0.0f;

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
