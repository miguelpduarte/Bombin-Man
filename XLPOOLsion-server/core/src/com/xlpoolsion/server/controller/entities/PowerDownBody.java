package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.EntityModel;
import com.xlpoolsion.server.model.entities.PowerDownModel;

/**
 * A concrete representation of an EntityBody representing a PowerDown
 */
public class PowerDownBody extends EntityBody{
    /**
     * Constructs a PowerDown body according to
     * a PowerDown model.
     *
     * @param world the physical world this power down belongs to.
     * @param model the model representing this power down.
     */
    public PowerDownBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 0.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(PowerDownModel.WIDTH / 2, PowerDownModel.HEIGHT / 2);

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
