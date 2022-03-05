package com.example.hackathon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Label logintext;

    @FXML
    protected void onLoginButtonClick() {
        logintext.setText("Logging in!");
    }
}