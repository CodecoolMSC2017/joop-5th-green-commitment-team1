package com.codecool.greencommitment.client;

// Java implementation for a client
// Save file as Client.java

import org.w3c.dom.Document;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    public static void runClient(String[] args) {
        int counter = 0;

        while(true){
            //Setting up socket connection
            ObjectOutputStream oos;
            String ip = args[0];
            Socket s = null ;

            String therm = "";
            if (counter > 3) {
                therm = "Exit";
                try{
                    s = new Socket(ip,5056) ;
                    oos = new ObjectOutputStream(s.getOutputStream( ));
                    oos.writeObject(therm);
                }catch (Exception e) {
                    System.out.println(e) ;
                }
            }else {
                //Creating the necessary objects
                DataGenerator dg = new DataGenerator();
                therm = dg.measureThermo();
            }

            DOMCreater dc = new DOMCreater();
            Document domToSend = dc.createDOM(Long.toString(System.currentTimeMillis()), therm, "Celsius");

            //Waiting for 3 seconds before next send
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{
                s = new Socket(ip,5056) ;
                System.out.println("Connected to : " + s);

                //SENDFILE
                oos = new ObjectOutputStream(s.getOutputStream( ));
                oos.writeObject(domToSend);
                counter++;
            }catch (Exception e) {
                System.out.println(e) ;
            }
        }
    }

}
