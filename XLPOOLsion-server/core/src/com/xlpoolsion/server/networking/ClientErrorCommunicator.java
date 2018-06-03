package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.ClientToServerMessage;
import com.xlpoolsion.common.ServerToClientMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Communicates an error to the client and awaits for its acknowledgement, or for a timeout
 */
class ClientErrorCommunicator {
    private final Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    private Thread messagePollingThread;

    /**
     * The time (in milliseconds) to wait for the client response until timing out
     */
    private static final int SOCKET_TIMEOUT_MS = 5000;

    ClientErrorCommunicator(Socket socket) {
        this.socket = socket;
        try {
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(SOCKET_TIMEOUT_MS);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        createStreams();
        sendMessage(new ServerToClientMessage(ServerToClientMessage.MessageType.SERVER_FULL));
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
     * Starts polling for client response in separate thread (read is blocking)
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
                            continue;
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("Socket timed out while waiting for ACK");
                        closeConnection();
                        return;
                    } catch (EOFException e) {
                        //e.printStackTrace();
                        System.out.println("Client we were waiting for ACK from disconnected");
                        closeConnection();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        continue;
                    }

                    if(msg.messageType == ClientToServerMessage.MessageType.ACK) {
                        System.out.println("Client acknowledged message");
                        closeConnection();
                        return;
                    } else {
                        System.out.println("Whoops, a different message was received");
                    }
                }
            }
        });

        messagePollingThread.start();
    }

    /**
     * Sends the given message to the client and flushes the output stream
     * @param msg Message to send to the client
     */
    private void sendMessage(ServerToClientMessage msg) {
        try {
            obj_out.writeObject(msg);
            obj_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            obj_in.close();
            obj_out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
