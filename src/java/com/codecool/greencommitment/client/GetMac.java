package com.codecool.greencommitment.client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GetMac {

    public String getMacAddress() {

        String macAddr = "";

        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            macAddr = sb.toString();


        } catch (UnknownHostException e) {
            e.printStackTrace();

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return macAddr;
    }
}
