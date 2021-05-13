package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.repositories.EtudiantRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;
import com.formaplus.utils.Utils;
import com.formaplus.utils.Validator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;

public class EtudiantEditController extends Controller implements Initializable {
	@FXML
	private Button saveButton;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneField;
	@FXML
	private DatePicker birthDayField;
	@FXML
	private ImageView photoImageView;
	@FXML
	private Button imageChooseButton;
	@FXML
	private ComboBox<String> sexeField;
	
	private int idEtu;
	
	private File file;
	
	
	
	private EtudiantRepository repo;
	
	public EtudiantEditController() {
		this.repo = new EtudiantRepository();
	}

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		
		Validator validator = new Validator();
		validator.notEmpty(lastNameField.getText(), "Le nom n'est pas valide")
				.notEmpty(firstNameField.getText(), "Le prénom est obligatoire")
				.isNumber(phoneField.getText(), "Le numéro de téléphone n'est pas valide")
				.minLength(phoneField.getText(), 8, "Le numéro de téléphone doit contenir au moins 8 caratères")
				.notEmpty(sexeField.getSelectionModel().getSelectedItem().toString(), "Le sexe est obligatoire")
				.isEmail(emailField.getText(), "L'email n'est pas valide")
				.isPastDate(birthDayField.getValue(), "La date de naissance n'est pas valide");
	    
		if (validator.isValid()) {
			Etudiant e = new Etudiant();
		    e.setDateNaissEtu(birthDayField.getValue());
		    e.setIdEtu(this.idEtu);
		    e.setEmailEtu(emailField.getText());
		    e.setNomEtu(lastNameField.getText());
		    e.setPrenomEtu(firstNameField.getText());
		    e.setSexeEtu(sexeField.getSelectionModel().getSelectedItem());
		    e.setTelEtu(Integer.parseInt(phoneField.getText()));
		    if(file != null) {
		    	try {
					e.setPhotoEtu(new FileInputStream(file));
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}
		    }
		    
		    if(repo.Save(e)) {
		    	AlertMessage.showInformation("Les informations de l'étudiant ont été mis à jour");
		    }
		} else {
			AlertMessage.showInformation(validator.getErrors().get(0));
		}
	}
	// Event Listener on Button[#imageChooseButton].onAction
	@FXML
	public void handleImageChooseButton(ActionEvent event) {
		file = LoadView.chooseImageDialog(imageChooseButton);
		if(file != null) photoImageView.setImage(new Image(file.toURI().toString(), 140, 130, true, true));
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		List<String> sexe = new ArrayList<String>();
		sexe.add("Masculin");
		sexe.add("Féminin");
		sexeField.setItems(FXCollections.observableArrayList(sexe));
	}
	
	public void setData(Object obj) {
		Etudiant e = (Etudiant)obj;
		lastNameField.setText(e.getNomEtu());
		firstNameField.setText(e.getPrenomEtu());
		emailField.setText(e.getEmailEtu());
		birthDayField.setValue(e.getDateNaissEtu());
		sexeField.getSelectionModel().select(e.getSexeEtu());
		phoneField.setText(String.valueOf(e.getTelEtu()));
		this.idEtu = e.getIdEtu();
		photoImageView.setImage(Utils.getImage(e.getPhotoEtu()));
	}
}
