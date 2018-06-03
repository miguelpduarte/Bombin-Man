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

import java.util.ArrayList;

import static com.xlpoolsion.server.networking.MultithreadedServer.MAX_CLIENTS;

/**
 * Controls the physics aspect of the game and state changes
 */
public class GameController {
    private static GameController instance = null;
    private BaseLevelController currentLevelController;

    /**
     * Gets the state in which the game is in
     * @return The state
     */
    public STATE getCurrentState() {
        return currentState;
    }

    /**
     * Ends the game
     */
    public void closeGame() {
        NetworkRouter.getInstance().closeServer();
        Gdx.app.exit();
    }

    /**
     * Sends the message to the client that he lost, and starts death animation for the player
     * @param playerModel The player that will die
     */
    void killPlayer(PlayerModel playerModel) {
        NetworkRouter.getInstance().sendToClient(playerModel.getId(), new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_LOST));
        playerModel.startDying();
    }

    /**
     * Sends the stunning message to all the players except the one who picked up the power
     * @param stunnerPlayer The player who will not be stunned
     */
    void playerStunnedOtherPlayers(PlayerModel stunnerPlayer) {
        currentLevelController.setAllStunnedExcept(stunnerPlayer);
        NetworkRouter.getInstance().sendToAllExcept(stunnerPlayer.getId(), new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_ARE_STUNNED));
    }

    /**
     * Stops the stunning of a player
     * @param playerId Player who will me unstunned
     */
    public void unstunPlayer(int playerId) {
        currentLevelController.unstun(playerId);
    }

    /**
     * Saves the players last information before death, used on the WinScreen
     */
    private ArrayList<PlayerModel> savedPlayersLastInfo;

    /**
     * Deals with the victory
     * @param winner_id Player who won
     */
    public void wonGame(int winner_id) {
        currentState = STATE.PLAYER_WON_GAME;
        //Saving players last info before everything is deleted
        ArrayList<PlayerModel> temp = currentLevelController.getModel().getPlayersLastInfo();
        savedPlayersLastInfo = new ArrayList<PlayerModel>();
        savedPlayersLastInfo.addAll(temp);

        currentLevelController.destroy();
        currentLevelController = null;

        //Safety
        NetworkRouter.getInstance().sendToAllExcept(winner_id, new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_LOST));

        NetworkRouter.getInstance().sendToClient(winner_id, new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_WON));
    }

    /**
     * Gets the ArrayList of the players last info before death
     * @return The arrayList of players
     */
    public ArrayList<PlayerModel> getPlayersLastInfo() {
        return savedPlayersLastInfo;
    }

    /**
     * Resets the game state
     */
    public void resetGame() {
        for(PlayerModel playerModel : savedPlayersLastInfo) {
            playerModel = null;
        }
        savedPlayersLastInfo.clear();
        savedPlayersLastInfo = null;
        currentState = STATE.LOBBY;
    }

    /**
     * Gets the players connected to the server
     * @return Array of booleans true, if connected false if not
     */
    public boolean[] getConnectedClients() {
        return NetworkRouter.getInstance().getConnectedClients();
    }

    /**
     * Enum of the different states of the game
     */
    public enum STATE {LOBBY, PLAYING, PLAYER_WON_GAME};

    private STATE currentState;

    //private final World world;
    /**
     * Maximum allowed players
     */
    public static final int MAX_PLAYERS = MAX_CLIENTS;

    private GameController() {
        currentState = STATE.LOBBY;
    }

    /**
     * Returns a singleton instance of a game controller
     * @return the singleton instance
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Updates the game
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {
        if(this.currentState == STATE.PLAYING) {
            this.currentLevelController.update(delta);
        }
    }

    /**
     * Sends the disconnect message to the player
     * @param playerId The id of the player that will receive the message
     */
    public void informPlayerDisconnect(int playerId) {
        if(this.currentState == STATE.PLAYING) {
            this.currentLevelController.informPlayerDisconnect(playerId);
        }
    }

    /**
     * Calls the function to deal with the movement of the player
     * @param playerId Player that will be moved
     * @param move_direction vector with the direction of the movement
     */
    public void movePlayer(int playerId, Vector2 move_direction) {
        currentLevelController.movePlayer(playerId, move_direction);
    }

    /**
     * Calls the function to place the bomb in the player position
     * @param playerId The player who placed the bomb
     */
    public void placeBomb(int playerId) {
        currentLevelController.placeBomb(playerId);
    }

    /**
     * Calls the creation of a powerup
     * @param brick Brick that was destroyed to give room for the power
     */
    public void createPowerUp(BreakableBrickModel brick){
        currentLevelController.createPowerUp(brick);
    }

    /**
     * Calls the creation of a powerdown
     * @param brick Brick that was destroyed to give room for the power
     */
    public void createPowerDown(BreakableBrickModel brick){
        currentLevelController.createPowerDown(brick);
    }

    /**
     * Calls the creation of a stunPower
     * @param brick Brick that was destroyed to give room for the power
     */
    public void createStunPower(BreakableBrickModel brick) {
        currentLevelController.createStunPower(brick);
    }

    /**
     * Calls the creation of an explosion
     * @param bomb That created the explosions
     */
    public void createExplosions(BombModel bomb) {
        currentLevelController.createExplosions(bomb);
    }

    /**
     * Calls the function to remove the bodies that were flagged for removal
     */
    public void removeFlagged() {
        currentLevelController.removeFlagged();
    }

    /**
     * Gets the BaseLevelModel
     * @return The BaseLevelModel
     */
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

    /**
     * Minimum allowed clients to start the game
     */
    public static final int MIN_CONNECTED_CLIENTS = 2;

    /**
     * Deals with the start of the game
     * @param level type of level that will be created
     */
    public void startGame(int level) {
        if(NetworkRouter.getInstance().getNConnectedClients() < MIN_CONNECTED_CLIENTS) {
            System.out.println("Can't start the game without at least " + MIN_CONNECTED_CLIENTS + " clients");
            return;
        } else {
            currentLevelController = new SimpleLevelController(NetworkRouter.getInstance().getConnectedClients());
            NetworkRouter.getInstance().sendToAll(new ServerToClientMessage(ServerToClientMessage.MessageType.START_GAME));
            currentState = STATE.PLAYING;
        }
    }
}
