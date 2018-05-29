package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager {
    private final Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    private Thread messagePollingThread;
    private int clientId;

    public ClientManager(Socket socket, int id) {
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
                        if(msg == null) {
                            //System.out.println("Message was null, continuing");
                            continue;
                        }
                    } catch (EOFException e) {
                        //e.printStackTrace();
                        System.out.println("Client " + clientId + " disconnected");
                        closeConnection();
                        NetworkRouter.getInstance().getServer().removeClient(clientId);
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

    public void sendMessage(ServerToClientMessage msg) {
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

    public void closeConnection() {
        messagePollingThread.interrupt();
        closeSocket();
    }

    public int getId() {
        return clientId;
    }
}
