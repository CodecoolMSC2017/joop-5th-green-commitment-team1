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
        
        int i = 1;
        Socket incoming = s;
        System.out.println("Spawning " + i);

        try {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                OutputStream outStream = incoming.getOutputStream();
                PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);


                out.println("Object received ");

                //RECIEVE and WRITE FILE
                if (ois.readObject() instanceof String) {
                    return;
                } else if (ois.readObject() instanceof Document) {
                    Document domRecieved = (Document) ois.readObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




