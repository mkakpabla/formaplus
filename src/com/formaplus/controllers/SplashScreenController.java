package com.formaplus.controllers;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.Db;
import com.formaplus.utils.LoadView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SplashScreenController implements Initializable {

	public Label lblLoading;
    public static Label lblLoadingg;
    
    @FXML
    public ProgressBar loadingBar;
    
    public static ProgressBar s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLoadingg = lblLoading;
        s = loadingBar;
    }

    public String checkFunctions(){

        final String[] message = {""};
        Thread t1 = new Thread(() -> {
            message[0] = "Intialisation de base de données";
            Platform.runLater(() -> {
            	lblLoadingg.setText(message[0]);
            	s.setProgress(0.3);
            });
            Connection connection = Db.getConnection();
            if(connection == null) {
            	AlertMessage.showInformation("Error", "Imposible de se connecter à la base de données");
            }
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Second Function";
            Platform.runLater(() -> {
            	lblLoadingg.setText(message[0]);
            	s.setProgress(0.6);
            	
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            message[0] = "Open Main Stage";
            Platform.runLater(() -> {
            	lblLoadingg.setText(message[0]);
            	s.setProgress(1);
            });

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        LoadView.showView("Login", "Login");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        });

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message[0];
    }
}
