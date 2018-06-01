package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;
import com.xlpoolsion.server.controller.GameController;

public class NetworkRouter {
    private static NetworkRouter instance = null;
    private MultithreadedServer server = null;

    private NetworkRouter() {

    }

    public static NetworkRouter getInstance() {
        if(instance == null) {
            instance = new NetworkRouter();
        }
        return instance;
    }

    void forwardMessage(int senderId, ClientToServerMessage msg) {
        //System.out.println("Router received message of type " + msg.messageType);

        //TEMPORARY
        if(GameController.getInstance().getCurrentState() != GameController.STATE.PLAYING) {
            //To prevent null pointer exceptions by doing what wouldnt be done anyway
            return;
        }

        switch (msg.messageType) {
            case CONTROLLER_SHAKE:
                System.out.println("Player " + senderId + " shaked his phone! Unstun please!");
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

    public void setServer(MultithreadedServer server) {
        this.server = server;
    }

    public MultithreadedServer getServer() {
        return server;
    }

    public void sendToAll(ServerToClientMessage msg) {
        if(server != null) {
            server.broadcast(msg);
        }
    }

    public void sendToClient(int clientId, ServerToClientMessage msg) {
        if(server != null) {
            server.sendToClient(clientId, msg);
        }
    }

    public void sendToAllExcept(int clientId, ServerToClientMessage msg) {
        if (server != null) {
            server.sendToAllExcept(clientId, msg);
        }
    }

    public void closeServer() {
        if (server != null) {
            server.closeServer();
            server = null;
        }
    }

    public boolean[] getConnectedClients() {
        if(server != null) {
            return server.getConnectedClients();
        } else {
            return new boolean[0];
        }
    }

    void informPlayerDisconnect(int clientId) {
        GameController.getInstance().informPlayerDisconnect(clientId);
    }
}
