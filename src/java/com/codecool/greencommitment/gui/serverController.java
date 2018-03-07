package com.codecool.greencommitment.gui;

import com.codecool.greencommitment.server.Charter;
import com.codecool.greencommitment.server.FileCollector;
import com.codecool.greencommitment.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class serverController {
    private Parent root;
    @FXML
    AnchorPane window;
    Thread tr;
    public void startServer()throws Exception{
        tr = new Thread(()-> {
            JOptionPane.showMessageDialog(null, "The server is running");
            try {
                Server.runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });tr.start();
    }

    public void showChart(){
        FileCollector files = new FileCollector(".");
        String[] arr = files.getXMLFileNames();
        Charter ex = null;
        try {
            ex = new Charter(arr);
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
