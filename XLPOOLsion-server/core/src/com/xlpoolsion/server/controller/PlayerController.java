package com.xlpoolsion.server.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.server.model.PlayerModel;

public class PlayerController {
    /**
     * The Box2D body that supports this body.
     */
    final Body body;


    public PlayerController(World world, PlayerModel playerModel) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerModel.getX(), playerModel.getY());
        //Switch to get rotation?
        bodyDef.angle = 0;

        body = world.createBody(bodyDef);
        body.setUserData(playerModel);
    }
}
