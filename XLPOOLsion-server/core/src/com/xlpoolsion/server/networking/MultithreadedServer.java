package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ServerToClientMessage;
import com.xlpoolsion.server.controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Multithreaded Server used for the game communications. Listens for connections in a separate thread (to not block the main thread used for the game) and
 * for each new connection passes the socket to a ClientManager or ClientErrorCommunicator, depending on the current status, that will handle
 * client communications.
 */
public class MultithreadedServer {
    /**
     * The maximum number of clients that can connect to the game
     */
    public static int MAX_CLIENTS = 4;

    /**
     * The server socket to use for communications
     */
    private ServerSocket svSocket;
    /**
     * The {@link .ClientManager}s responsible for handling client communications
     */
    private ClientManager[] clientManagers = new ClientManager[MAX_CLIENTS];
    /**
     * The thread in which we will listen for connections
     */
    private Thread connectionListeningThread;

    /**
     * Creates a new MultithreadedServer
     */
    public MultithreadedServer() {
        System.out.println("Creating server in IP: " + NetworkInfo.getInstance().getServerIP() + " at port " + NetworkInfo.getInstance().getServerPort());
        try {
            svSocket = new ServerSocket(NetworkInfo.getInstance().getServerPort());
        } catch (IOException e) {
            System.out.println("Could not create server, exiting");
            e.printStackTrace();
            System.exit(1);
        }
        startListening();
    }

    /**
     * Starts listening for incoming connections in a new thread (ServerSocket.accept is a blocking call)
     */
    private void startListening() {
        connectionListeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //Continuously listen to connections and connect to them in a new thread (ClientManager is in a separate thread)
                    try {
                        Socket socket = svSocket.accept();
                        System.out.println("Created socket: " + socket.getInetAddress().getHostAddress());

                        if(GameController.getInstance().getCurrentState() == GameController.STATE.PLAYING) {
                            //If server is playing, there can be no connections added for safety reasons
                            System.out.println("Attempted to connect while server is playing");
                            //Opening a client error communicator that will tell the client the server was full and wait for an ACK, or resolve on timeout or disconnect
                            new ClientErrorCommunicator(socket);
                            continue;
                        }

                        int index = getFirstAvailableIndex();
                        if (index == -1) {
                            //Max n clients reached, signal that to client
                            System.out.println("Max clients reached!");
                            //Opening a client error communicator that will tell the client the server was full and wait for an ACK, or resolve on timeout or disconnect
                            new ClientErrorCommunicator(socket);
                            continue;
                        }

                        clientManagers[index] = new ClientManager(socket, index);
                        System.out.println("Client " + index + " connected");
                    } catch (SocketException e) {
                        //Server closed but thread didn't stop
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        connectionListeningThread.start();
    }

    /**
     * Gets the first available index in the clientManagers array, in which the new ClientManager will be inserted.
     * @return Returns the first available index in the clientManagers array, or -1 in case it is full.
     */
    private int getFirstAvailableIndex() {
        for (int i = 0; i < clientManagers.length; ++i) {
            if (clientManagers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Forwards the passed message to all the connected clients
     * @param msg Message to forward to all the clients
     */
    void broadcast(ServerToClientMessage msg) {
        for (int i = 0; i < clientManagers.length; ++i) {
            if (clientManagers[i] != null) {
                clientManagers[i].sendMessage(msg);
            }
        }
    }

    /**
     * Forwards the passed message to all but the client with the given id
     * @param clientId Id of the client to not send the message to
     * @param msg Message to send
     */
    void sendToAllExcept(int clientId, ServerToClientMessage msg) {
        for(int i = 0; i < clientManagers.length; ++i) {
            if(i == clientId) {
                continue;
            }
            if(clientManagers[i] != null) {
                clientManagers[i].sendMessage(msg);
            }
        }
    }

    /**
     * Forwards the passed message to the specified client
     * @param clientId Id of the client to send the message to
     * @param msg Message to send
     */
    void sendToClient(int clientId, ServerToClientMessage msg) {
        if (clientId < MAX_CLIENTS && clientManagers[clientId] != null) {
            clientManagers[clientId].sendMessage(msg);
        }
    }

    /**
     * Removes a client from the internal array
     * @param clientId Id of the client to remove
     */
    void removeClient(int clientId) {
        if (clientId < MAX_CLIENTS) {
            clientManagers[clientId] = null;
        }
    }

    /**
     * Closes the server, by first closing the connection with each client as well
     */
    void closeServer() {
        for (ClientManager clientManager : clientManagers) {
            if (clientManager != null) {
                clientManager.closeConnection();
            }
        }

        connectionListeningThread.interrupt();

        try {
            svSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the number of connected clients
     * @return Returns the number of connected clients
     */
    int getNConnectedClients() {
        int res = 0;
        for (ClientManager clientManager : clientManagers) {
            if (clientManager != null) {
                res++;
            }
        }
        return res;
    }

    /**
     * Returns an array representative of the current client connections
     * @return Returns an array that represents the current client connections. Each index represents the client with the corresponding id, being true if he is connected of false if not.
     */
    boolean[] getConnectedClients() {
        boolean[] res = new boolean[MAX_CLIENTS];
        for(int i = 0; i < clientManagers.length; ++i) {
            if(clientManagers[i] != null) {
                res[i] = true;
            }
        }
        return res;
    }
}
