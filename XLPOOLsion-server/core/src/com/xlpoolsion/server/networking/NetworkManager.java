package com.xlpoolsion.server.networking;

import com.xlpoolsion.common.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager {
    private ServerSocket svSocket;
    private String myIP = "172.30.2.190";
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream obj_in;


    public NetworkManager(int port) throws IOException {
        svSocket = new ServerSocket(port);
        System.out.println("I think i am created");
    }

    public void acceptClient() throws IOException {
        System.out.println("Trying to accept");
        clientSocket = svSocket.accept();
        System.out.println("I think i am accept");
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        obj_in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void sendOutput() throws IOException {
        if(clientSocket == null) {
            return;
        }
        out.println("Sending server info");
    }

    public void readInput() throws ClassNotFoundException, IOException {
        if(clientSocket == null) {
            return;
        }

        Message msg = (Message) obj_in.readObject();

        System.out.printf("Server received message:\naFloat: %f\nanInt: %d\nString: %s\n",
                msg.getaFloat(), msg.getAnInt(), msg.getString());
    }

    public void closeServer() {
        closeClient();

        if(svSocket.isClosed()) {
            return;
        }

        try {
            svSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeClient() {
        if(clientSocket == null || clientSocket.isClosed()) {
            return;
        }

        if(in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(out != null) {
            out.close();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
