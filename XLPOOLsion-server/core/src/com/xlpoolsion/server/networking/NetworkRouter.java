package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.Message;

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

    void forwardMessage(Message msg) {
        System.out.println("Router received message of type " + msg.messageType);
    }

    //TODO: Debate about this structure
    public void setServer(MultithreadedServer server) {
        this.server = server;
    }

    public void sendToAll(Message msg) {
        if(server == null) {
            return;
        }

        server.sendToAll(msg);
    }

    public void closeServer() {
        server.closeServer();
        server = null;
    }
}
