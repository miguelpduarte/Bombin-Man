package com.xlpoolsion.server.networking;

import java.io.IOException;
import java.net.Socket;

public class NetworkInfo {
    private static final String IP_VERIFICATION_URL = "google.com";
    private static final int IP_VERIFICATION_PORT = 80;

    private String serverIP;
    private final int serverPort = 9876;

    private static NetworkInfo instance = null;

    private NetworkInfo() {
        try {
            Socket ip_verification_socket = new Socket(IP_VERIFICATION_URL, IP_VERIFICATION_PORT);
            serverIP = ip_verification_socket.getLocalAddress().getHostAddress();
            ip_verification_socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No internet connection!");
            System.exit(2);
        }
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public static NetworkInfo getInstance() {
        if(instance == null) {
            instance = new NetworkInfo();
        }
        return instance;
    }
}
