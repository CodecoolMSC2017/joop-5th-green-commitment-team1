package com.codecool.greencommitment.common;

import com.codecool.greencommitment.client.Client;
import com.codecool.greencommitment.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class common {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        String scanString = scan.nextLine();
        

        if (scanString.equals("server")){
            Server.runServer();

        } else if(scanString.equals("client")) {
            Client.runClient();

        }else{
            System.out.println("That's not an option");
        }
    }
}
