package com.formaplus.controllers;


import com.formaplus.utils.LoadView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainController {
	
	@FXML
    private BorderPane borderPane;
	
	@FXML
    private Button payButton;
	
	@FXML
    private Button homeButton;
	
	@FXML
	private Button inscriptionButton;

    @FXML
    private Button btnEtudiants;
    
    @FXML
    private Button btnUtilisateurs;
    
    @FXML
    private AnchorPane content;

    @FXML
    public void handleBtnEtudiantClick(ActionEvent event) {
    	setCenterContent("Etudiants");
    }
    
    @FXML
    public void handleBtnFormationsClick(ActionEvent event) {
    	setCenterContent("Formations");
    }
    
    @FXML
    public void handleBtnUtilisateursClicked(ActionEvent event) {
    	setCenterContent("Utilisateurs");
    }
    
    @FXML
    public void handleInscriptionButtonAction(ActionEvent event) {
    	setCenterContent("Inscriptions");
    }
    
    @FXML
    public void handleBtnSessionsClicked(ActionEvent event) {
    	setCenterContent("Sessions");
    }
    
    @FXML
    public void handlePayButtonAction(ActionEvent event) {
    	setCenterContent("Paiements");
    }
    
    @FXML
    public void handleHomeButtonAction(ActionEvent event) {
    	setCenterContent("Home");
    }
    
    private void setCenterContent(String name) {
    	BorderPane parent = (BorderPane)LoadView.loadFxml(name);
    	parent.setPrefHeight(content.getHeight());
    	parent.setPrefWidth(content.getWidth());
    	borderPane.setCenter(parent);
    }

}