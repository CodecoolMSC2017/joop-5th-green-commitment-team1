package com.codecool.greencommitment.gui;

import com.codecool.greencommitment.server.Charter;
import com.codecool.greencommitment.server.FileCollector;
import com.codecool.greencommitment.server.Server;
import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class serverController {


    public void startServer()throws Exception{
        JOptionPane.showMessageDialog(null,"The server is running");
        Server.runServer();
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
}
