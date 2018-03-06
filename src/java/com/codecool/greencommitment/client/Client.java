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
        Socket s = null;
        String ip = args[0];
        try {
            s = new Socket(ip,5056) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            //Creating the necessary objects
            DataGenerator dg = new DataGenerator();
            String therm = dg.measureThermo();
            DOMCreater dc = new DOMCreater();
            Document domToSend = dc.createDOM(Long.toString(System.currentTimeMillis()),therm,"Celsius");

            //Waiting for 3 seconds before next send
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Setting up socket connection
            ObjectOutputStream oos;



            try{

                OutputStream outStream = s.getOutputStream() ;
                System.out.println("Connected to : " + s);

                //SENDFILE

                oos = new ObjectOutputStream(s.getOutputStream( ));
                oos.writeObject(domToSend);
            }catch (Exception e) {
                System.out.println(e) ;
            }
        }
    }

}
