package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientManager {
    private final Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;

    public ClientManager(Socket socket) {
        this.socket = socket;
        createStreams();
        pollForMessages();
    }

    private void createStreams() {
        try {
            obj_in = new ObjectInputStream(socket.getInputStream());
            obj_out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts polling for client messages in separate thread (read is blocking)
     */
    private void pollForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = null;
                    try {
                        msg = (Message) obj_in.readObject();
                    } catch (EOFException e) {
                        System.out.println("EOF found, sleeping some more");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        continue;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Server received message");

                    NetworkRouter.getInstance().forwardMessage(msg);
                }
            }
        }).start();
    }

    public void sendMessage(Message msg) {
        try {
            obj_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            obj_in.close();
            obj_out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
