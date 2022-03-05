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
    public TextField serverIP;
    public TextField serverPort;
    public TextField test;
    public ChoiceBox serverInfo;
    @FXML
    private Label logintext;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        String server = (String) serverInfo.getValue();
        logintext.setText(server);
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) logintext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();



        // Send login message to: serverIP:serverPort with displayName
    }
}
