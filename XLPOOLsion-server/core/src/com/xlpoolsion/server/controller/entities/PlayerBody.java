package com.xlpoolsion.server.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.entities.PlayerModel;

/**
 * A concrete representation of an EntityBody representing a Player.
 */
public class PlayerBody extends EntityBody {
    /**
     * Constructs a Player body according to
     * a Player model.
     *
     * @param world the physical world this player belongs to.
     * @param model the model representing this player.
     */
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

    /**
     * Makes the player move up by changing its vertical velocity
     * @param speedFactor
     */
    public void moveUp(float speedFactor) {
        body.setLinearVelocity(body.getLinearVelocity().x, ((PlayerModel) body.getUserData()).getCurrentSpeed() * speedFactor);
    }

    /**
     * Makes the player move down by changing its vertical velocity
     * @param speedFactor
     */
    public void moveDown(float speedFactor) {
        body.setLinearVelocity(body.getLinearVelocity().x, ((PlayerModel) body.getUserData()).getCurrentSpeed() * speedFactor);
    }

    /**
     * Makes the player move left by changing its horizontal velocity
     * @param speedFactor
     */
    public void moveLeft(float speedFactor) {
        body.setLinearVelocity(((PlayerModel) body.getUserData()).getCurrentSpeed() * speedFactor, body.getLinearVelocity().y);
    }

    /**
     * Makes the player move right by changing its horizontal velocity
     * @param speedFactor
     */
    public void moveRight(float speedFactor) {
        body.setLinearVelocity(((PlayerModel) body.getUserData()).getCurrentSpeed() * speedFactor, body.getLinearVelocity().y);
    }

    /**
     * Stops the player's horizontal movement by changing its x velocity to 0
     */
    public void stopX() {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }

    /**
     * Stops the player's vertical movement by changing its y velocity to 0
     */
    public void stopY() {
        body.setLinearVelocity(body.getLinearVelocity().x, 0);
    }
}
