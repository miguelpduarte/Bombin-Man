package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;
import com.xlpoolsion.server.controller.GameController;

/**
 * Singleton class responsible for routing messages to and from the GameController, using a {@link .MutlithreadedServer}.
 */
public class NetworkRouter {
    private static NetworkRouter instance = null;

    /**
     * The current server being used for communication
     */
    private MultithreadedServer server = null;

    private NetworkRouter() {

    }

    /**
     * Returns the instance of the NetworkRouter (singleton class)
     * @return Instance of the NetworkRouter
     */
    public static NetworkRouter getInstance() {
        if(instance == null) {
            instance = new NetworkRouter();
        }
        return instance;
    }

    /**
     * Forwards a message from the server to the GameController, with the necessary pre-processing
     * @param senderId The id of the client that sent this message
     * @param msg The message to forward
     */
    void forwardMessage(int senderId, ClientToServerMessage msg) {
        //System.out.println("Router received message of type " + msg.messageType);

        //TEMPORARY
        if(GameController.getInstance().getCurrentState() != GameController.STATE.PLAYING) {
            //To prevent null pointer exceptions by doing what wouldnt be done anyway
            return;
        }

        switch (msg.messageType) {
            case CONTROLLER_SHAKE:
                //Marking player as no longer stunned and sending that same information to the client
                GameController.getInstance().unstunPlayer(senderId);
                sendToClient(senderId, new ServerToClientMessage(ServerToClientMessage.MessageType.YOU_ARE_NO_LONGER_STUNNED));
                break;
            case PLAYER_MOVE:
                GameController.getInstance().movePlayer(senderId, msg.move_direction);
                break;
            case PRESSED_PLACE_BOMB:
                GameController.getInstance().placeBomb(senderId);
                break;
            case PRESSED_GRAB_BOMB:
                System.out.println("Grabbing bombs is WIP!");
                break;
        }
    }

    /**
     * Sets the current server to the passed one
     * @param server The server to use for routing purposes
     */
    public void setServer(MultithreadedServer server) {
        this.server = server;
    }

    /**
     * Sends the passed message to all the connected clients
     * @param msg Message to send to all clients
     */
    public void sendToAll(ServerToClientMessage msg) {
        if(server != null) {
            server.broadcast(msg);
        }
    }

    /**
     * Sends the passed message to the client with the specified id
     * @param clientId Id of the client to send the message to
     * @param msg Message to send to the client
     */
    public void sendToClient(int clientId, ServerToClientMessage msg) {
        if(server != null) {
            server.sendToClient(clientId, msg);
        }
    }

    /**
     * Sends the passed message to all clients except the one with the passed id
     * @param clientId Id of the client that will not receive the message
     * @param msg Message to send
     */
    public void sendToAllExcept(int clientId, ServerToClientMessage msg) {
        if (server != null) {
            server.sendToAllExcept(clientId, msg);
        }
    }

    /**
     * Closes the server
     */
    public void closeServer() {
        if (server != null) {
            server.closeServer();
            server = null;
        }
    }

    /**
     * Gets which clients are connected. In case the server is closed, returns an empty array.
     * @return Returns a boolean array in which each index corresponds to a certain client and indicates if he is connected or not.
     */
    public boolean[] getConnectedClients() {
        if(server != null) {
            return server.getConnectedClients();
        } else {
            return new boolean[0];
        }
    }

    /**
     * Informs the GameController when a certain client disconnects
     * @param clientId The id of the disconnected client
     */
    void informPlayerDisconnect(int clientId) {
        GameController.getInstance().informPlayerDisconnect(clientId);
    }

    /**
     * Gets the number of clients connected to the server.
     * @return Returns the number of clients connected to the server. In case the server is closed, returns 0.
     */
    public int getNConnectedClients() {
        if(server != null) {
            return server.getNConnectedClients();
        } else {
            return 0;
        }
    }

    /**
     * Removes the client with given id from the server data structures.
     * @param clientId Client to remove
     */
    public void removeClient(int clientId) {
        if(server != null) {
            server.removeClient(clientId);
        }
    }
}
