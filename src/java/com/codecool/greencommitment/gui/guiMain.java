package com.codecool.greencommitment.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class guiMain extends Application{
    Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        primaryStage.setTitle("Measurement App");
        primaryStage.setScene(new Scene(root,640,480));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public void guimain(){
        guiMain gm = new guiMain();
        clientController cc = new clientController();
        gm.launch();
    }
}
