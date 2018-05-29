package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ServerToClientMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MultithreadedServer {
    public static int MAX_CLIENTS = 4;

    private ServerSocket svSocket;
    private ClientManager[] clientManagers = new ClientManager[MAX_CLIENTS];
    private Thread connectionListeningThread;

    public MultithreadedServer() throws IOException {
        System.out.println("Creating server in IP: " + NetworkInfo.getInstance().getServerIP() + " at port " + NetworkInfo.getInstance().getServerPort());
        svSocket = new ServerSocket(NetworkInfo.getInstance().getServerPort());
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

                        int index = getFirstAvailableIndex();
                        if (index == -1) {
                            //Max n clients reached, signal that to client
                            System.out.println("Max clients reached!");
                            continue;
                        }

                        //TODO: Add restrictions here -> Same client cannot connect twice, limit max number of connections, etc
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

    private int getFirstAvailableIndex() {
        for (int i = 0; i < clientManagers.length; ++i) {
            if (clientManagers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void broadcast(ServerToClientMessage msg) {
        for (int i = 0; i < clientManagers.length; ++i) {
            if (clientManagers[i] != null) {
                clientManagers[i].sendMessage(msg);
            }
        }
    }

    public void sendToAllExcept(int clientId, ServerToClientMessage msg) {
        for(int i = 0; i < clientManagers.length; ++i) {
            if(i == clientId) {
                continue;
            }
            if(clientManagers[i] != null) {
                clientManagers[i].sendMessage(msg);
            }
        }
    }

    public void sendToClient(int clientId, ServerToClientMessage msg) {
        if (clientId < MAX_CLIENTS && clientManagers[clientId] != null) {
            clientManagers[clientId].sendMessage(msg);
        }
    }

    public void removeClient(int clientId) {
        if (clientId < MAX_CLIENTS) {
            clientManagers[clientId] = null;
        }
    }

    public void closeServer() {
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

    public int getNConnectedClients() {
        int res = 0;
        for (ClientManager clientManager : clientManagers) {
            if (clientManager != null) {
                res++;
            }
        }
        return res;
    }

    public boolean[] getConnectedClients() {
        boolean[] res = new boolean[MAX_CLIENTS];
        for(int i = 0; i < clientManagers.length; ++i) {
            if(clientManagers[i] != null) {
                res[i] = true;
            }
        }
        return res;
    }
}
