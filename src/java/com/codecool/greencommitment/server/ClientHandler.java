package com.codecool.greencommitment.server;

import org.w3c.dom.Document;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientHandler extends Thread {

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

        /*try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("/home/robertn/Documents/java_develop/git_repos/Green_Commitment/joop-5th-green-commitment-team1/src/java/com/codecool/greencommitment/server/horn.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {

        BufferedInputStream bis;
        BufferedOutputStream bos;
        int num;

        try {
            int i = 1;
            Socket incoming = s;
            System.out.println("Spawning " + i);

            try {
                try {

                    InputStream inStream = incoming.getInputStream();
                    OutputStream outStream = incoming.getOutputStream();

                    BufferedReader inm = new BufferedReader(new InputStreamReader(inStream));
                    PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);


                    out.println("File received ");

                    //RECIEVE and WRITE FILE
                    byte[] receivedData = new byte[8192];
                    bis = new BufferedInputStream(incoming.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream("measurements.xml"));
                    //length = bis.read(receivedData);
                    while ((num = bis.read(receivedData)) != -1) {
                        bos.write(receivedData, 0, num);
                    }
                    Document recievedDOM = BytesToDOM.parseToDOM(receivedData);
                    System.out.println("\n\n\n"+recievedDOM.getElementsByTagName("time").item(0).getTextContent()+"\n\n\n");
                    bos.close();
                    bis.close();

                    /*File receivedFile = new File("measurements.xml");
                    long receivedLen = receivedFile.length();
                    out.println("ACK: Length of received file = " + receivedLen);
                    System.out.println("Length of received file = " + receivedLen);*/

                } finally {
                    incoming.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}





