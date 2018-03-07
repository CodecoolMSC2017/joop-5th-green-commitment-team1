package com.codecool.greencommitment.client;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetMac {

    public String getMacAddress() {

        String macAddr = "";

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements())
            {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if(mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++)
                    {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    macAddr = sb.toString();
                }
            }
        } catch (SocketException e){
            e.printStackTrace();
        }
        return macAddr;
    }
}
