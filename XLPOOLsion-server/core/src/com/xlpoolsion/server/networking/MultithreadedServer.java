package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultithreadedServer {
    private ServerSocket svSocket;
    private ArrayList<ClientManager> clientManagers = new ArrayList<ClientManager>();
    private Thread connectionListeningThread;

    public MultithreadedServer() throws IOException {
        System.out.println("Creating server in IP: " + NetworkInfo.getInstance().getServerIP() + " at port " + NetworkInfo.getInstance().getServerPort());
        svSocket = new ServerSocket(NetworkInfo.getInstance().getServerPort());
        //Makes the call blocking (default behaviour from what I've seen), need to investigate further
        //TODO: Investigate further and decide best course of action here
        svSocket.setSoTimeout(0);
        startListening();
    }

    /**
     * Starts listening for incoming connections in a new thread (ServerSocket.accept is a blocking call)
     */
    private void startListening() {
        System.out.println("I will now listen for connections");

        connectionListeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //Continuously listen to connections and connect to them in a new thread (ClientManager is in a separate thread)
                    try {
                        System.out.println("Waiting to accept connection");
                        Socket socket = svSocket.accept();
                        System.out.println("Created socket: " + socket.getInetAddress().getHostAddress());
                        //TODO: Add restrictions here -> Same client cannot connect twice, limit max number of connections, etc
                        clientManagers.add(new ClientManager(socket));
                        System.out.println("Connection added! Now at " + clientManagers.size() + " connections!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        connectionListeningThread.start();
    }

    public void sendToAll(Message msg) {
        System.out.println("Sending message to all clients");
        for(ClientManager clientManager : clientManagers) {
            if(clientManager.isOpen()){
                clientManager.sendMessage(msg);
            } else {
                //Because we can't detect this mid reading polling
                clientManager.closeConnection();
                clientManagers.remove(clientManager);
            }
        }
    }

    public void closeServer() {
        for (ClientManager clientManager : clientManagers) {
            clientManager.closeConnection();
        }

        try {
            connectionListeningThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            svSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
