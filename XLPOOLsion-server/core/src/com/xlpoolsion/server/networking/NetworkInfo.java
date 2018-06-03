package com.xlpoolsion.server.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkInfo {

    private String serverIP;
    private static final int serverPort = 9876;

    private static NetworkInfo instance = null;

    /**
     * Iterates through the existing <code>NetworkInterfaces</code> in order to figure out the Localhost LAN IP address.
     * Created due to ambiguity of <code>InetAddress.getLocalHost</code> in Linux systems.
     *
     * @return Scans all IP addresses on all network interfaces on the host machine to determine the IP address
     *         most likely to be the machine's LAN address. If the machine has multiple IP addresses, this method will prefer
     *         a site-local IP address (e.g. 192.168.x.x or 10.10.x.x, usually IPv4) if the machine has one and will return the
     *         first site-local address if the machine has more than one. If the machine does not have a site-local
     *         address, this method will simply return the first non-loopback address found (IPv4 or IPv6). In the event that a non-loopback address cannot be found,
     *         the result of <code>InetAddress.getLocalHost</code> is returned as a fallback.
     * @throws UnknownHostException If the LAN address of the machine cannot be found.
     */
    private static InetAddress getLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all Network Interfaces
            Enumeration<NetworkInterface> interfaces_enumeration = NetworkInterface.getNetworkInterfaces();
            while(interfaces_enumeration.hasMoreElements()) {
                NetworkInterface ni = interfaces_enumeration.nextElement();
                //Iterate all InetAddresses for each NI
                Enumeration<InetAddress> inet_addr_enumeration = ni.getInetAddresses();
                while(inet_addr_enumeration.hasMoreElements()) {
                    InetAddress inet_addr = inet_addr_enumeration.nextElement();

                    if(!inet_addr.isLoopbackAddress()) {
                        if(inet_addr.isSiteLocalAddress()) {
                            //This is a non loopback site local address
                            return inet_addr;
                        } else if(candidateAddress == null) {
                            //Found non loopback adress that might not be site local
                            //Save as candidate in case nothing better is found
                            candidateAddress = inet_addr;
                            //Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            //only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }

            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its Network Interface (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address
                return candidateAddress;
            }

            // At this point, we did not find a non-loopback address.
            // Fall back to returning what InetAddress.getLocalHost() returns
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    private NetworkInfo() {
        try {
            serverIP = getLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    private static final String IP_VERIFICATION_URL = "google.com";
    private static final int IP_VERIFICATION_PORT = 80;

    /**
     * Before used code to check for the Localhost LAN IP, but that requires internet access to check
     * @return The Localhost LAN IP address
     */
    private String getLANIP_usingInternet() {
        try {
            Socket ip_verification_socket = new Socket(IP_VERIFICATION_URL, IP_VERIFICATION_PORT);
            String lanIP = ip_verification_socket.getLocalAddress().getHostAddress();
            ip_verification_socket.close();
            return lanIP;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No internet connection!");
            return null;
        }
    }

    public String getServerIP() {
        return serverIP;
    }

    int getServerPort() {
        return serverPort;
    }

    public static NetworkInfo getInstance() {
        if(instance == null) {
            instance = new NetworkInfo();
        }
        return instance;
    }
}
