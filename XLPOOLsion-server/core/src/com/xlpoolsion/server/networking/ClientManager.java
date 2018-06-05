package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Responsible for managing the connection to a certain client, through its respective Socket
 */
class ClientManager {
    private final Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    private Thread messagePollingThread;
    private int clientId;

    ClientManager(Socket socket, int id) {
        this.socket = socket;
        this.clientId = id;
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        createStreams();
        pollForMessages();
    }

    private void createStreams() {
        try {
            obj_out = new ObjectOutputStream(socket.getOutputStream());
            obj_in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts polling for client messages in separate thread (read is blocking)
     */
    private void pollForMessages() {
        messagePollingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientToServerMessage msg;
                while(true) {
                    try {
                        msg = (ClientToServerMessage) obj_in.readObject();
                        if (msg == null) {
                            continue;
                        }

                    } catch (SocketException e) {
                        //Socket was closed but thread is still running, terminate it
                        //Just in case remove from the router data and inform disconnection
                        System.out.println("Client " + clientId + " disconnected!");
                        NetworkRouter.getInstance().informPlayerDisconnect(clientId);
                        closeSocket();
                        NetworkRouter.getInstance().removeClient(clientId);
                        return;
                    } catch (EOFException e) {
                        System.out.println("Client " + clientId + " disconnected");
                        NetworkRouter.getInstance().informPlayerDisconnect(clientId);
                        closeSocket();
                        NetworkRouter.getInstance().removeClient(clientId);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        continue;
                    }

                    NetworkRouter.getInstance().forwardMessage(clientId, msg);
                }
            }
        });

        messagePollingThread.start();
    }

    /**
     * Sends the given message to the client and flushes the output stream
     * @param msg Message to send to the client
     */
    void sendMessage(ServerToClientMessage msg) {
        try {
            obj_out.writeObject(msg);
            obj_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSocket() {
        try {
            obj_in.close();
            obj_out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current client connection
     */
    void closeConnection() {
        messagePollingThread.interrupt();
        closeSocket();
    }
}
