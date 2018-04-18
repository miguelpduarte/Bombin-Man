package com.xlpoolsion.client.networking;

import com.xlpoolsion.common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class NetworkManager {
    private ObjectOutputStream obj_out;
    private Socket socket;
    //private String myIP = "172.30.2.190";
    //Port 9021

    //Communication
    private PrintWriter out;
    private BufferedReader in;

    public NetworkManager(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        if(socket.isConnected()) {
            System.out.println("I am connekt!");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            obj_out = new ObjectOutputStream(socket.getOutputStream());
        } else {
            System.out.println("I am not connekt :(");
            throw new ConnectException();
        }
    }

    public void sendOutput() {
        out.println("Sending info");
    }

    public void sendMessage(Message msg) throws IOException {
        obj_out.writeObject(msg);
    }

    public void readInput() throws IOException {
        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server said: " + fromServer);
            if (fromServer.equals("Bye."))
                break;
        }
    }
}
