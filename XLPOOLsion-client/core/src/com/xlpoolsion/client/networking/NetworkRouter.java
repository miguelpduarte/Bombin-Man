package com.xlpoolsion.client.networking;

import com.xlpoolsion.common.Message;

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

    void forwardMessage(Message msg) {
        System.out.println("Router in client received message of type " + msg.messageType);
    }

    public void sendToServer(Message msg) {
        if(connection == null) {
            return;
        }

        connection.sendMessage(msg);
    }
}
