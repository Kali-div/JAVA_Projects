package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class s2Ctrl {

    @FXML
    private TextField hostId;
    @FXML
    private TextField portId;
    @FXML
    private Label status;
    @FXML
    private ListView<String> testView;
    @FXML
    private TextField msgId;

    private String successMsg = "-fx-text-fill: GREEN;";
    private String errorMsg = "-fx-text-fill: RED;";

    private PrintWriter pw;

    @FXML
    private void onCon() {
        String host = hostId.getText();
        int port = Integer.parseInt(portId.getText());

        try {
            Socket socket = new Socket(host, port); // Connect to server

            if (socket.isConnected()) {

                status.setText("Server online");
                status.setStyle(successMsg);

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                pw = new PrintWriter(os, true);

                new Thread(() -> {
                    while (true) {
                        try {
                            String rez = br.readLine();
                            if (rez != null) {
                                Platform.runLater(() -> testView.getItems().add(rez));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } 

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("start the server first");
            status.setText("Server offline");
            status.setStyle(errorMsg);
        }
    }

    @FXML
    private void onSend() {
        String message = msgId.getText();
        if (pw != null) {
            pw.println(message);
        }
    }
}
