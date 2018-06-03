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

/**
 * Represents a client to server connection
 */
public class Connection {
    private Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    /**
     * The predefined server communication port
     */
    private static final int server_port = 9876;
    /**
     * The thread in which to listen for messages (read is blocking)
     */
    private Thread messageListeningThread;

    /**
     * Opens a connection to the server in the given IP
     * @param ip IP to connect to
     * @throws IOException In case the connection to the server was unsuccessful
     */
    public Connection(String ip) throws IOException {
        socket = new Socket(ip, server_port);
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

    /**
     * Sends the given message to the server, flushing the output stream
     * @param msg Message to send to the server
     */
    public void sendMessage(ClientToServerMessage msg) {
        try {
            obj_out.writeObject(msg);
            obj_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the server
     */
    void close() {
        closeSocket();
        messageListeningThread.interrupt();
    }

    /**
     * Parses a zero-padded IP to one in the correct format
     * @param connectIp IP to parse
     * @return Parsed IP, for example 192168001016 becomes 192.168.1.16
     */
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
