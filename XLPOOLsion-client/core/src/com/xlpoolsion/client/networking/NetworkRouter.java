package com.xlpoolsion.client.networking;

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

    //TODO: Debate about this structure
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    void forwardMessage(ServerToClientMessage msg) {
        System.out.println("Router in client received message of type " + msg.messageType);
    }

    public void sendToServer(ClientToServerMessage msg) {
        System.out.println("Sending message of type " + msg.messageType);
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
}
