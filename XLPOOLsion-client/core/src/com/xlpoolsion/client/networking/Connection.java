package com.xlpoolsion.client.networking;

import com.xlpoolsion.common.Message;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectInputStream obj_in;
    private ObjectOutputStream obj_out;
    //private String myIP = "172.30.2.190";
    //Port 9021

    private Thread messageListeningThread;

    public Connection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);

        if(socket.isConnected()) {
            System.out.println("I am connekt!");
        } else {
            System.out.println("I am not connekt :(");
        }

        try {
            obj_in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Obj in stream creation exception");
            e.printStackTrace();
        }
        try {
            obj_out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Obj out stream creation exception");
            e.printStackTrace();
        }

        pollForMessages();
    }

    /**
     * Polls the server for messages in separate thread (read is blocking)
     */
    private void pollForMessages() {
        messageListeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting to poll for messages");

                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message msg = null;
                    try {
                        msg = (Message) obj_in.readObject();
                    } catch (EOFException e) {
                        System.out.println("Got an EOF, sleeping a bit");
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

                    System.out.println("Client received message of type: " + msg.messageType);

                    NetworkRouter.getInstance().forwardMessage(msg);
                }
            }
        });

        messageListeningThread.start();
    }

    public void sendMessage(Message msg) {
        try {
            obj_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            messageListeningThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
