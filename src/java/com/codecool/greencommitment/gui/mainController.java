package com.codecool.greencommitment.gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainController {
    private Parent root;
    @FXML AnchorPane window;

    public void setClientScene()throws Exception {
        root = FXMLLoader.load(getClass().getResource("clientStage.fxml"));
        Stage stage = (Stage) window.getScene().getWindow();
        stage.setScene(new Scene(root,640,480));
        stage.show();
    }

    public void setServerScene()throws Exception {
        root = FXMLLoader.load(getClass().getResource("startServerStage.fxml"));
        Stage stage = (Stage) window.getScene().getWindow();
        stage.setScene(new Scene(root,640,480));
        stage.show();
    }

}
