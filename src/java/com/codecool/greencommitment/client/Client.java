package com.codecool.greencommitment.client;

// Java implementation for a client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Client class
public class Client
{
    public static void runClient(String[] args) throws IOException
    {
        try
        {
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
            while (true)
            {
                System.out.println(gm.getMacAddress());
                System.out.println(dis.readUTF());
                String tosend = Float.toString(dg.measureThermo());
                dos.writeUTF(tosend);
                TimeUnit.SECONDS.sleep(3);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
