package com.xlpoolsion.client.networking;

import com.xlpoolsion.client.controller.GameController;
import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connection {
    private Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    //Port 9876

    private Thread messageListeningThread;

    public Connection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        try {
            socket.setTcpNoDelay(true);
        } catch(SocketException e) {
            e.printStackTrace();
        }

        createStreams();
        GameController.getInstance().waitForServer();
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
     * Polls the server for messages in separate thread (read is blocking)
     */
    private void pollForMessages() {
        messageListeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting to poll for messages");
                ServerToClientMessage msg;
                while (true) {
                    try {
                        msg = (ServerToClientMessage) obj_in.readObject();
                    } catch (SocketException e) {
                        //Socket was closed but thread is still running, terminate it
                        return;
                    } catch (EOFException e) {
                        System.out.println("Lost connection to server");
                        closeSocket();
                        GameController.getInstance().signalLostConnection();
                        NetworkRouter.getInstance().terminateConnection();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        continue;
                    }

                    //System.out.println("Client received message of type: " + msg.messageType);

                    if(msg.messageType == ServerToClientMessage.MessageType.SERVER_FULL) {
                        //Server full should be handled internally (in this class)
                        System.out.println("Received server full, sending ACK and closing");
                        sendMessage(new ClientToServerMessage(ClientToServerMessage.MessageType.ACK));
                        closeSocket();
                        GameController.getInstance().signalServerFull();
                        NetworkRouter.getInstance().terminateConnection();
                        return;
                    }

                    NetworkRouter.getInstance().forwardMessage(msg);
                }
            }
        });

        messageListeningThread.start();
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

    public void sendMessage(ClientToServerMessage msg) {
        try {
            obj_out.writeObject(msg);
            obj_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        closeSocket();
        messageListeningThread.interrupt();
    }

    public static String parseIP(String connectIp) {
        if(connectIp.length() != 12) {
            return null;
        }

        StringBuilder res = new StringBuilder(connectIp);
        res.insert(3, ".");
        res.insert(7, ".");
        res.insert(11, ".");

        String temp_str = res.toString();
        String[] cenas = temp_str.split("\\.");

        String final_str = "";
        for(String s : cenas) {
            final_str += "" + Integer.parseInt(s) + ".";
        }

        final_str = final_str.substring(0, final_str.length()-1);

        return final_str;
    }
}
