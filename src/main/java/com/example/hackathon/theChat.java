package com.example.hackathon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class theChat {

    public TextField writeMessageBox;

    @FXML
    protected void resetTheText() throws IOException {
        writeMessageBox.setText("Write Message Here");
    }




}
