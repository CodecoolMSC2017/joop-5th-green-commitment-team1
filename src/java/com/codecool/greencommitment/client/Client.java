package com.codecool.greencommitment.client;

// Java implementation for a client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    public static void runClient(String[] args) {

        while(true){
            DataGenerator dg = new DataGenerator();
            String therm = dg.measureThermo();
            XMLWriter XMLW = new XMLWriter();
            XMLW.saveToXML("clientData.xml",Long.toString(System.currentTimeMillis()),therm,"Celsius");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedInputStream bis;
            BufferedOutputStream bos;
            int num;
            byte[] byteArray;
            File f = new File("clientData.xml");
            String ip = args[0];
            Socket s = null ;
            try{
                s = new Socket(ip,5056) ;
                InputStream inStream = s.getInputStream() ;
                OutputStream outStream = s.getOutputStream() ;
                System.out.println("Connected to : " + s);

                BufferedReader inm = new BufferedReader(new InputStreamReader(inStream));
                PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

                for (String x : args) {

                    out.println(f);
                    long len = f.length();
                    System.out.println(inm.readLine());
                    System.out.println("Sent File length = " + len);

                    //SENDFILE
                    bis = new BufferedInputStream(new FileInputStream(f));
                    bos = new BufferedOutputStream(s.getOutputStream( ));
                    byteArray = new byte[8192];
                    while ((num = bis.read(byteArray)) != -1){
                        bos.write(byteArray,0,num);
                    }

                    bos.close();
                    bis.close();

                    System.out.println(inm.readLine());
                }
            }catch (Exception e) {
                System.out.println(e) ;
            }
        }
    }

}
