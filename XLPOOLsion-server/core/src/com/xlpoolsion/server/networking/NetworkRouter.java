package com.xlpoolsion.server.networking;

import com.badlogic.gdx.Gdx;
import com.xlpoolsion.common.Message;
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

    void forwardMessage(int senderId, Message msg) {
        //System.out.println("Router received message of type " + msg.messageType);

        //TEMPORARY
        if(GameController.getInstance().getCurrentState() != GameController.STATE.PLAYING) {
            //To prevent null pointer exceptions by doing what wouldnt be done anyway
            return;
        }

        if(msg.messageType == Message.MessageType.PLAYER_MOVE) {
            GameController.getInstance().movePlayer(senderId, msg.move_direction, Gdx.graphics.getDeltaTime());
        } else if(msg.messageType == Message.MessageType.PRESSED_PLACE_BOMB) {
            GameController.getInstance().placeBomb(senderId);
        }
    }

    //TODO: Debate about this structure
    public void setServer(MultithreadedServer server) {
        this.server = server;
    }

    public MultithreadedServer getServer() {
        return server;
    }

    public void sendToAll(Message msg) {
        if(server == null) {
            return;
        }

        server.broadcast(msg);
    }

    public void sendToClient(int clientId, Message msg) {
        server.sendToClient(clientId, msg);
    }

    public void closeServer() {
        server.closeServer();
        server = null;
    }
}
