package com.xlpoolsion.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.xlpoolsion.common.ServerToClientMessage;
import com.xlpoolsion.server.controller.levels.BaseLevelController;
import com.xlpoolsion.server.controller.levels.SimpleLevelController;
import com.xlpoolsion.server.model.entities.BombModel;
import com.xlpoolsion.server.model.entities.BreakableBrickModel;
import com.xlpoolsion.server.model.entities.PlayerModel;
import com.xlpoolsion.server.model.levels.BaseLevelModel;
import com.xlpoolsion.server.networking.NetworkRouter;

import static com.xlpoolsion.server.networking.MultithreadedServer.MAX_CLIENTS;

public class GameController {
    private static GameController instance = null;
    private BaseLevelController currentLevelController;

    public STATE getCurrentState() {
        return currentState;
    }

    public void closeGame() {
        NetworkRouter.getInstance().closeServer();
        Gdx.app.exit();
    }

    void killPlayer(PlayerModel playerModel) {
        NetworkRouter.getInstance().sendToClient(playerModel.getId(), new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_LOST));
        playerModel.startDying();
    }

    void playerStunnedOtherPlayers(PlayerModel stunnerPlayer) {
        currentLevelController.setAllStunnedExcept(stunnerPlayer);
        NetworkRouter.getInstance().sendToAllExcept(stunnerPlayer.getId(), new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_ARE_STUNNED));
    }

    public void unstunPlayer(int playerId) {
        currentLevelController.unstun(playerId);
    }

    public enum STATE {WAITING_FOR_CONNECTIONS, PLAYING, PLAYER_WON_GAME, ALL_PLAYERS_DISCONNECTED};

    private STATE currentState;

    //private final World world;
    public static final int MAX_PLAYERS = MAX_CLIENTS;

    private GameController() {
        currentState = STATE.WAITING_FOR_CONNECTIONS;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void update(float delta) {
        if(this.currentState == STATE.PLAYING) {
            this.currentLevelController.update(delta);
        }
    }

    public void informPlayerDisconnect(int playerId) {
        if(this.currentState == STATE.PLAYING) {
            this.currentLevelController.informPlayerDisconnect(playerId);
        }
    }

    public void movePlayer(int playerId, Vector2 move_direction) {
        currentLevelController.movePlayer(playerId, move_direction);
    }

    public void placeBomb(int playerId) {
        currentLevelController.placeBomb(playerId);
    }

    public void createPowerUp(BreakableBrickModel brick){
        currentLevelController.createPowerUp(brick);
    }

    public void createPowerDown(BreakableBrickModel brick){
        currentLevelController.createPowerDown(brick);
    }

    public void createStunPower(BreakableBrickModel brick) {
        currentLevelController.createStunPower(brick);
    }

    public void createExplosions(BombModel bomb) {
        currentLevelController.createExplosions(bomb);
    }

    public void removeFlagged() {
        currentLevelController.removeFlagged();
    }

    public BaseLevelModel getLevelModel() {
        return currentLevelController.getModel();
    }

    /**
     * Just for debug purposes (Debug Camera)
     * @return
     */
    public World getWorld() {
        return currentLevelController.getWorld();
    }

    private static final int MIN_CONNECTED_CLIENTS = 1;

    public void startGame(int level) {
        if(NetworkRouter.getInstance().getServer().getNConnectedClients() < MIN_CONNECTED_CLIENTS) {
            System.out.println("Can't start the game without at least " + MIN_CONNECTED_CLIENTS + " clients");
            return;
        } else {
            //This will in fact be another thing, as this will be abstract
            currentLevelController = new SimpleLevelController(NetworkRouter.getInstance().getServer().getConnectedClients());
            NetworkRouter.getInstance().sendToAll(new ServerToClientMessage(ServerToClientMessage.MessageType.START_GAME));
            currentState = STATE.PLAYING;
        }
    }
}
