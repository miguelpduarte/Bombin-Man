package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.PlayerModel;

public class PlayerBody extends EntityBody {

    public PlayerBody(World world, PlayerModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        //Creating fixtures
        float density = 0.0f;
        float friction = 0.0f;
        float restitution = 0.0f;

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(PlayerModel.WIDTH / 2, (PlayerModel.HEIGHT / 2) * 0.4f,
                            new Vector2(0, -(PlayerModel.HEIGHT / 2) * 0.4f), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        polyShape.dispose();
    }

    public void moveUp() {
        body.setLinearVelocity(body.getLinearVelocity().x, ((PlayerModel) body.getUserData()).getCurrentSpeed());
    }

    public void moveDown() {
        body.setLinearVelocity(body.getLinearVelocity().x, -((PlayerModel) body.getUserData()).getCurrentSpeed());
    }

    public void moveLeft() {
        body.setLinearVelocity(-((PlayerModel) body.getUserData()).getCurrentSpeed(), body.getLinearVelocity().y);
    }

    public void moveRight() {
        body.setLinearVelocity(((PlayerModel) body.getUserData()).getCurrentSpeed(), body.getLinearVelocity().y);
    }

    public void stopX() {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }

    public void stopY() {
        body.setLinearVelocity(body.getLinearVelocity().x, 0);
    }
}
