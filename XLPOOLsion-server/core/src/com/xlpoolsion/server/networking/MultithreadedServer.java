package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultithreadedServer {
    private static final int PORT = 9876;
    private ServerSocket svSocket;
    private ArrayList<ClientManager> clientManagers = new ArrayList<ClientManager>();

    public MultithreadedServer() throws IOException {
        svSocket = new ServerSocket(PORT);
        startListening();
    }

    /**
     * Starts listening for incoming connections in a new thread (ServerSocket.accept is a blocking call)
     */
    private void startListening() {
        System.out.println("I will now listen for connections");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //Continuously listen to connections and connect to them in a new thread (ClientManager is in a separate thread)
                    try {
                        System.out.println("Waiting to accept connection");
                        Socket socket = svSocket.accept();
                        clientManagers.add(new ClientManager(socket));
                        System.out.println("Connection added! Now at " + clientManagers.size() + " connections!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendToAll(Message msg) {
        System.out.println("Sending message to all clients");
        for(ClientManager clientManager : clientManagers) {
            clientManager.sendMessage(msg);
        }
    }

    public void closeServer() {
        for (ClientManager clientManager : clientManagers) {
            clientManager.closeConnection();
        }

        try {
            svSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
