package com.codecool.greencommitment.client;

// Java implementation for a client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {
    public static void runClient(String[] args) throws IOException {
        try {
            XMLWriter XMLW = new XMLWriter();
            Scanner scn = new Scanner(System.in);
            DataGenerator dg = new DataGenerator();
            GetMac gm = new GetMac();

            // getting ip from command line argument
            String ip = args[0];


            Socket s = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                System.out.println(gm.getMacAddress());
                System.out.println(dis.readUTF());
                String therm = dg.measureThermo();
                dos.writeUTF(therm);
                TimeUnit.SECONDS.sleep(3);
                XMLW.saveToXML("clientData.xml", Long.toString(System.currentTimeMillis()), therm, "Celsius");

                // If client sends exit,close this connection
                // and then break from the while loop


                // printing date or time as requested by client
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources
            /*scn.close();
            dis.close();
            dos.close();
        */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
