package com.xlpoolsion.server.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.xlpoolsion.server.model.GameModel;
import com.xlpoolsion.server.model.PlayerModel;

public class GameController implements ContactListener {
    private static GameController instance = null;

    private final World world;
    private final PlayerBody player;

    /**
     * The map width in meters.
     */
    public static final int GAME_WIDTH = 100;

    /**
     * The map height in meters.
     */
    public static final int GAME_HEIGHT = 100;

    private GameController() {
        instance = this;
        world = new World(new Vector2(0, 0), true);

        //Creating bodies
        player = new PlayerBody(world, GameModel.getInstance().getPlayer());

        //
        world.setContactListener(this);
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private float accumulator = 0;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    public void update(float delta) {
        GameModel.getInstance().update(delta);

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }

        //Updating bodies
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            //TODO: Use EntityModel or similar instead of PlayerModel
            //verifyBounds(body);
            ((PlayerModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            //((PlayerModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    public void movePlayerUp(float delta) {
        player.moveUp();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.UP);
    }

    public void movePlayerDown(float delta) {
        player.moveDown();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.DOWN);
    }

    public void movePlayerLeft(float delta) {
        player.moveLeft();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.LEFT);
    }

    public void movePlayerRight(float delta) {
        player.moveRight();
        ((PlayerModel) player.getUserData()).setMoving(true);
        ((PlayerModel) player.getUserData()).setOrientation(PlayerModel.Orientation.RIGHT);
    }

    public void stopPlayer(float delta) {
        player.stop();
        ((PlayerModel) player.getUserData()).setMoving(false);
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof PlayerModel) {
            System.out.println("Body A is player and collision ocurred");
        }

        if (bodyB.getUserData() instanceof PlayerModel) {
            System.out.println("Body B is player and collision ocurred");
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
