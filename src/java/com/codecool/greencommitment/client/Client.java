package com.codecool.greencommitment.client;

import org.w3c.dom.Document;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {
    private static int errorCounter = 1;

    public static void runClient(String[] args, int numOfMeasurements) {
        int counter = 0;
        Socket s = null;
        String ip = args[0];
        try {
            s = new Socket(ip, 5056);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            //Setting up socket connection
            ObjectOutputStream oos;

            String therm = "";
            if (counter >= numOfMeasurements) {
                therm = "Exit";
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(s.getOutputStream());
                    osw.write(therm);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //Creating the necessary objects
                DataGenerator dg = new DataGenerator();
                therm = dg.measureThermo();
            }

            DOMCreater dc = new DOMCreater();
            Document domToSend = dc.createDOM(Long.toString(System.currentTimeMillis()), therm, "Celsius");

            //Waiting for 3 seconds before next send
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Setting up socket connection

            try {
                //SENDFILE
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(domToSend);
                counter++;
                System.out.println("Object sent to: " + s);
            } catch (SocketException e) {
                if (errorCounter >= 3) {
                    System.out.println("\nUnable to connect to the server, the program has been termianted");
                    System.exit(-1);
                } else {
                    System.out.println("\nError, the server's socket is closed, attempting to reconnect");
                }
                errorCounter++;
            } catch (UnknownHostException e) {
                System.out.println("Unknow host");
                System.exit(-2);
            } catch (FileNotFoundException e) {
                System.out.println("File not found by the Client");
                System.exit(-3);
            } catch (IOException e) {
                System.out.println("IOException at the Client side");
                System.exit(-4);
            }
        }
    }

}
