package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.BrickModel;

/**
 * A concrete representation of an EntityBody representing a Solid Brick.
 */
public class BrickBody extends  EntityBody {

    /**
     * Constructs a Brick body according to
     * a Brick model.
     *
     * @param world the physical world this brick belongs to.
     * @param model the model representing this brick.
     */
    public BrickBody(World world, BrickModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 0.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(BrickModel.WIDTH / 2, BrickModel.HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        polyShape.dispose();
    }
}
