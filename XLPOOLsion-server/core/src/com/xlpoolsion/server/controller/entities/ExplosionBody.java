package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.ExplosionModel;

/**
 * A concrete representation of an EntityBody representing an Explosion
 */
public class ExplosionBody extends EntityBody {
    /**
     * Constructs an Explosion body according to
     * an Explosion model.
     *
     * @param world the physical world this explosion belongs to.
     * @param model the model representing this explosion.
     */
    public ExplosionBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 0.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(ExplosionModel.WIDTH / 2, ExplosionModel.HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        polyShape.dispose();

        //Pretend this isn't here :(
        body.setLinearVelocity(Float.MIN_VALUE, 0f);
    }
}
