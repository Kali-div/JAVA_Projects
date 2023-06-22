package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class s1Ctrl {
    
    @FXML
    private TextField userID;
    @FXML
    private PasswordField pwdID;
    @FXML
    private Label error;
    
    private String errorMessage = "-fx-text-fill: RED;";
    private String errorStyle = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;";
    
    @FXML
    protected void onLogin() throws IOException {
        if (userID.getText().equals("admin") && pwdID.getText().equals("gg")) {
            Stage stage = (Stage) userID.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("s2.fxml"));
            Scene s2 = new Scene(fxmlLoader.load());
            stage.setScene(s2);
        } else if (userID.getText().isBlank() || pwdID.getText().isBlank()) {
            error.setText("Insert your login data");
            setErrorStyles();
        } else {
            error.setText("Login Error");
            setErrorStyles();
        }
    }
    
    private void setErrorStyles() {
        if (userID.getText().isBlank()) {
            userID.setStyle(errorStyle);
        } else if (pwdID.getText().isBlank()) {
            pwdID.setStyle(errorStyle);
        }
        error.setStyle(errorMessage);
    }
}
