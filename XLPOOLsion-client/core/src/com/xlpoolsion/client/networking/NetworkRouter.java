package com.xlpoolsion.client.networking;

import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

public class NetworkRouter {
    private static NetworkRouter instance = null;
    private Connection connection = null;

    private NetworkRouter() {
    }

    public static NetworkRouter getInstance() {
        if(instance == null) {
            instance = new NetworkRouter();
        }
        return instance;
    }

    public void setConnection(Connection connection) {
        //To ensure that no duplicate connections are attempted
        endConnection();
        this.connection = connection;
    }

    void forwardMessage(ServerToClientMessage msg) {
        System.out.println("Router in client received message of type " + msg.messageType);
        switch (msg.messageType) {
            case START_GAME:
                GameController.getInstance().startGame();
                break;
            case YOU_WON:
                System.out.println("This player won (in router)!!");
                GameController.getInstance().signalWonGame();
                //DONT FORGET THIS IS HERE, DONT CRASH IN SERVER PLEASE
                endConnection();
                break;
            case YOU_LOST:
                System.out.println("This player lost (was killed by player " + 420 + ") - WIP");
                GameController.getInstance().signalLostGame(420);
                //DONT FORGET THIS IS HERE, DONT CRASH IN SERVER PLEASE
                endConnection();
                break;
        }
    }

    public void sendToServer(ClientToServerMessage msg) {
        //System.out.println("Sending message of type " + msg.messageType);
        if(connection == null) {
            System.out.println("Not connected");
            return;
        }

        connection.sendMessage(msg);
    }

    public void endConnection() {
        if(connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * To be used by the Connection, to signal it closed itself due to errors or server full/game end and thus network router should simply stop using it
     */
    public void terminateConnection() {
        //Connection already closed itself
        connection = null;
    }
}
