package com.codecool.greencommitment.common;

import com.codecool.greencommitment.client.Client;
import com.codecool.greencommitment.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class common {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        String scanString = scan.nextLine();
        scan.close();

        if (scanString.equals("server")){
            Server s = new Server();
            s.runServer();
        } else {
            Client c = new Client();
            c.runClient();
        }
    }
}
