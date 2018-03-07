package com.codecool.greencommitment.server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Thread t;
    static ServerSocket ss;
    public static void runServer() throws IOException
    {
        // server is listening on port 5056
        ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (!ss.isClosed())
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                t = new ClientHandler(s, dis, dos);


                // Invoking the start() method
                t.start();

            }
            catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    public static void stopServer()throws Exception{

        ss.close();

    }
}
