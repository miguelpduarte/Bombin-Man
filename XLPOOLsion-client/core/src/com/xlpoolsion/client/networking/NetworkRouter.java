package com.xlpoolsion.client.networking;

import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

/**
 * Singleton class responsible for routing messages to and from the GameController, using a {@link .Connection}.
 */
public class NetworkRouter {
    private static NetworkRouter instance = null;
    /**
     * Current connection to the server
     */
    private Connection connection = null;

    private NetworkRouter() {
    }

    /**
     * Returns the current NetworkRouter singleton instance
     * @return The current singleton instance of this class
     */
    public static NetworkRouter getInstance() {
        if(instance == null) {
            instance = new NetworkRouter();
        }
        return instance;
    }

    /**
     * Sets the current connection to the given one, after terminating the current one, if it exists
     * @param connection The new connection to use
     */
    public void setConnection(Connection connection) {
        //To ensure that no duplicate connections are attempted
        endConnection();
        this.connection = connection;
    }

    /**
     * Forwards a server message to the GameController, after doing the correct preprocessing
     * @param msg The server message to process and send to the GameController
     */
    void forwardMessage(ServerToClientMessage msg) {
        switch (msg.messageType) {
            case YOU_ARE_STUNNED:
                GameController.getInstance().signalStunned();
                break;
            case YOU_ARE_NO_LONGER_STUNNED:
                GameController.getInstance().signalUnstunned();
                break;
            case START_GAME:
                GameController.getInstance().startGame();
                break;
            case YOU_WON:
                System.out.println("This player won (in router)!!");
                GameController.getInstance().signalWonGame();
                endConnection();
                break;
            case YOU_LOST:
                System.out.println("This player lost (was killed by player " + 420 + ") - WIP");
                GameController.getInstance().signalLostGame(420);
                endConnection();
                break;
        }
    }

    /**
     * Routes a message to the server
     * @param msg Message to send to the server
     */
    public void sendToServer(ClientToServerMessage msg) {
        if(connection == null) {
            System.out.println("Not connected");
            return;
        }

        connection.sendMessage(msg);
    }

    /**
     * Terminates the current connection, if it exists
     */
    private void endConnection() {
        if(connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * To be used by the Connection, to signal it closed itself due to errors or server full/game end and thus network router should simply stop using it
     */
    public void terminateConnection() {
        connection = null;
    }
}
