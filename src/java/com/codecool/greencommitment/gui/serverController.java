package com.codecool.greencommitment.gui;

import com.codecool.greencommitment.server.Charter;
import com.codecool.greencommitment.server.FileCollector;
import com.codecool.greencommitment.server.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class serverController {
    private Parent root;
    @FXML
    TextField portTextField;
    @FXML
    AnchorPane window;
    @FXML
    ComboBox idChoiceBox;
    Thread tr;
    FileCollector files;
    public void startServer()throws Exception{
        files = new FileCollector(".");
        String[] tmpArray = files.getXMLFileNames();
        String[] resultArray = new String[files.getXMLFileNames().length+1];
        resultArray[0]="all";
        for(int i=0;i<files.getXMLFileNames().length;i++){
            resultArray[i+1]=tmpArray[i];
        }

        idChoiceBox.setItems(FXCollections.observableArrayList(resultArray

        ));
        tr = new Thread(()-> {

            JOptionPane.showMessageDialog(null, "The server is running");
            try {
                Server.runServer(Integer.parseInt(portTextField.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });tr.start();
    }

    public void showChart(){


        Charter ex = null;
        String[] item = {(String)idChoiceBox.getSelectionModel().getSelectedItem()};
        try {
            if(((String)idChoiceBox.getSelectionModel().getSelectedItem()).equals("all")){
                String[] arr = files.getXMLFileNames();
                ex = new Charter(arr);
            }else{
                ex = new Charter(item);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ex.setVisible(true);
    }

    public void goBack()throws Exception{
        root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        Stage stage = (Stage) window.getScene().getWindow();
        stage.setScene(new Scene(root,640,480));
        stage.show();
    }

    public void stopServer() throws Exception {

        Server.stopServer();
        JOptionPane.showMessageDialog(null,"Server has shut down");

    }
}
