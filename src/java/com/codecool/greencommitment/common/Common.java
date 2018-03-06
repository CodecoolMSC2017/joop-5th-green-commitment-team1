package com.codecool.greencommitment.common;

import com.codecool.greencommitment.client.*;
import com.codecool.greencommitment.server.*;

import java.io.IOException;
import java.util.Scanner;

public class Common {

    public static void main(String[] args) throws IOException {
        SensorManager sensors = new SensorManager();

        System.out.println("Choose 'client' or 'server' mode:");

        Scanner scan = new Scanner(System.in);
        String scanString = scan.nextLine();


        if (scanString.equals("server")){
            Server.runServer();

        } else if(scanString.equals("client")) {
            System.out.println("How many measuerements you want to send ?");
            int numOfMeasurements = scan.nextInt();
            Client.runClient(args,numOfMeasurements);

        }else{
            System.out.println("That's not an option");
        }
    }
}
