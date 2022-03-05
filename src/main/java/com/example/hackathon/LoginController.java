package com.example.hackathon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField displayName;
    public String serverIP;
    public int serverPort;
    public TextField test;
    public ChoiceBox serverInfo;
    @FXML
    private Label logintext;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        String server = (String) serverInfo.getValue();
        if (server == "Server 1"){
            serverIP = "108.59.85.131";
            serverPort = 4000;
        }
        else if (server == "Server 2"){
            serverIP = "108.59.85.131";
            serverPort = 4000; //no server 2 yet
        }
        else{
            serverIP = "108.59.85.131";
            serverPort = 4000;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) displayName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();



        // Send login message to: serverIP:serverPort with displayName
    }
}
