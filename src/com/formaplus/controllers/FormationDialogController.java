package com.formaplus.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.formaplus.dao.models.Formation;
import com.formaplus.dao.repositories.FormationRepository;
import com.formaplus.utils.AlertMessage;


public class FormationDialogController extends Controller {
	
	private int idFormation = 0;
	
	
	@FXML
	@NotEmpty(message = "Le libellé ne peur être vide")
	private TextField libFormationField;
	
	
	@FXML
	@Positive(message = "La durée n'est pas valide")
	private TextField dureeFormationField;
	
	
	@FXML
	@Positive(message = "Le prix n'est pas valide")
	private TextField prixFormationField;
	
	
	@FXML
	private Button saveFormationButton;
	
	

	// Event Listener on Button[#saveFormationButton].onAction
	@FXML
	public void handleSaveFormationButtonAction(ActionEvent event) {
	    if (this.validate()) {
	    	Formation formation = new Formation();
			formation.setIdFormation(idFormation);
			formation.setLibFormation(libFormationField.getText());
			formation.setDureeFormation(Integer.parseInt(dureeFormationField.getText()));
			formation.setPrixFormation(Double.parseDouble(prixFormationField.getText()));
	    	if(new FormationRepository().Save(formation)) {
	    		if(idFormation == 0) {
	    			AlertMessage.showInformation("Les informations de la formation ont été mis a jour");
	    		} else {
	    			AlertMessage.showInformation("Formation sauvegarder avec success");
	    		}
				
			}
	    }
	}
	
	@Override
	public void setData(Object obj) {
		Formation formation = (Formation)obj;
		libFormationField.setText(formation.getLibFormation());
		dureeFormationField.setText(String.valueOf(formation.getDureeFormation()));
		prixFormationField.setText(String.valueOf(formation.getPrixFormation()));
		idFormation = formation.getIdFormation();
	}
	
	
	
}
