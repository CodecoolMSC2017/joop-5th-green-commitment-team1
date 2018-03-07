package com.codecool.greencommitment.gui;

import com.codecool.greencommitment.client.Client;
import com.codecool.greencommitment.client.DOMCreater;
import com.codecool.greencommitment.client.DataGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class clientController {
    private Parent root;
    static String valueToPrint;
    private static int errorCounter = 1;

    @FXML
    TextField ipTextField,portTextField,numOfMeasurementsTextField;
    @FXML
    Label ipLabel,portLabel,numLabel;
    @FXML
    TextArea textArea;
    @FXML
    AnchorPane window;

    public void connect(){

            Thread t = new Thread(() -> {


                int counter = 0;
                Socket s = null;
                String ip = ipTextField.getText();


                try

                {
                    s = new Socket(ip, 5056);
                }catch(ConnectException cE){
                    JOptionPane.showMessageDialog(null,"The server is not running");
                } catch (IOException e) {
                    e.printStackTrace();
                }



                while (true)

                {
                    //Setting up socket connection
                    ObjectOutputStream oos;


                    String therm = "";
                    if (counter >= Integer.parseInt(numOfMeasurementsTextField.getText())) {
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
                        updateStatus("Value sent to " + s.getInetAddress() + ":  " + therm + " C");


                    }

                    DOMCreater dc = new DOMCreater();
                    Document domToSend = dc.createDOM(Long.toString(System.currentTimeMillis()), therm, "Celsius");

                    //Waiting for 3 seconds before next send
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Setting up socket connection

                    try {
                        //SENDFILE
                        oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(domToSend);

                        counter++;

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
            });

            t.start();


    }

    public void print(String value){
        textArea.setText(textArea.getText()+"\n"+value);
    }

    private void updateStatus(String message){
        if(Platform.isFxApplicationThread()){
            textArea.setText(textArea.getText()+"\n"+message);
        }else{
            Platform.runLater(()-> textArea.setText(textArea.getText()+"\n"+message));
        }
    }

    public void goBack()throws Exception{
        root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        Stage stage = (Stage) window.getScene().getWindow();
        stage.setScene(new Scene(root,640,480));
        stage.show();
    }
}



