package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.EtudiantRepository;
import com.formaplus.dao.repositories.FormationRepository;
import com.formaplus.dao.repositories.InscriptionRepository;
import com.formaplus.dao.repositories.SessionRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;
import com.formaplus.utils.Validator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;

public class InscriptionDialogController implements Initializable {
	@FXML
	private Button saveButton;
	
	@FXML
    private ImageView photoImageView;
	@FXML
	private TextField idField;
	@FXML
	private Button searchButton;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private ComboBox<String> sexeField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField priceField;
	@FXML
	private ComboBox<Session> sessionField;
	@FXML
	private ComboBox<Formation> formationField;
	@FXML
	private DatePicker birthDayField;
	@FXML
	private Button imageChooseButton;
	
	private File file;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initComboBox();
		this.sexeField.getSelectionModel().select(0);
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
				.isNumber(priceField.getText(), "Le prix entrer n'est pas valide")
				.isPastDate(birthDayField.getValue(), "La date de naissance n'est pas valide")
				.notNull(sessionField.getValue(), "La session de la formation est obligatoire")
				.notNull(formationField.getValue(), "Veiller selectionner la formation");
		
		if(validator.isValid()) {
			if(file != null) {
				Etudiant etudiant = new Etudiant();
				etudiant.setNomEtu(lastNameField.getText());
				etudiant.setPrenomEtu(firstNameField.getText());
				etudiant.setEmailEtu(this.emailField.getText());
				etudiant.setDateAjout(LocalDate.now());
				etudiant.setTelEtu(Integer.parseInt(this.phoneField.getText()));
				etudiant.setSexeEtu(sexeField.getValue());
				try {
					etudiant.setPhotoEtu(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				etudiant.setDateNaissEtu(birthDayField.getValue());
				
				Inscription insc = new Inscription();
				insc.setEtudiant(etudiant);
				insc.setFormation(formationField.getSelectionModel().getSelectedItem());
				insc.setSession(sessionField.getSelectionModel().getSelectedItem());
				insc.setPrixInsc(Double.parseDouble(priceField.getText()));
				insc.setDateInsc(LocalDate.now());
				if(new InscriptionRepository().Save(insc)) {
					AlertMessage.showInformation("Inscription enrégistrer avec succes");
					Node  source = (Node)event.getSource(); 
				    Stage stage  = (Stage)source.getScene().getWindow();
				    stage.close();
				}
			} else AlertMessage.showWarning("La photo de l'étudiant est requis");
			
		} else {
			List<String> errors = validator.getErrors();
			AlertMessage.showWarning(errors.get(0));
		}
		
		
	}
	// Event Listener on Button[#searchButton].onAction
	@FXML
	public void handleSearchButtonAction(ActionEvent event) {
		Etudiant etudiant = new EtudiantRepository().GetById(Integer.parseInt(idField.getText()));
		this.lastNameField.setText(etudiant.getNomEtu());
		this.firstNameField.setText(etudiant.getPrenomEtu());
		this.emailField.setText(etudiant.getEmailEtu());
		this.phoneField.setText(String.valueOf(etudiant.getTelEtu()));
		this.birthDayField.setValue(etudiant.getDateNaissEtu());
		this.sexeField.getSelectionModel().select(etudiant.getSexeEtu());
		
		try {
			OutputStream os = new FileOutputStream(new File("etudiant.jpg"));
			byte[] content = new byte[1024];
			int size = 0;
			while((size = etudiant.getPhotoEtu().read(content)) != -1) {
				os.write(content, 0, size);
			}
			Image image = new Image("file:etudiant.jpg", 140, 130, true, true);
			photoImageView.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.disableFields();
	}
	// Event Listener on Button[#imageChooseButton].onAction
	@FXML
	public void handleImageChooseButton(ActionEvent event) {
		file = LoadView.chooseImageDialog(imageChooseButton);
		if(file != null) photoImageView.setImage(new Image(file.toURI().toString(), 140, 130, true, true));
	}
	
	@FXML
    public void handlleSessionFieldAction(ActionEvent event) {
		Session s = sessionField.getSelectionModel().getSelectedItem();
		formationField.setItems(new FormationRepository().GetAllWhereSessionId(s.getIdSession()));
    }
	
	
	public void initComboBox() {
		List<Session> sessions = new SessionRepository().GetAll().stream()
											.filter(s -> s.getDateFin().isAfter(LocalDate.now())).collect(Collectors.toList());
		sessionField.setItems(FXCollections.observableArrayList(sessions));
		List<String> sexe = new ArrayList<String>();
		sexe.add("Masculin");
		sexe.add("Féminin");
		sexeField.setItems(FXCollections.observableArrayList(sexe));
		
	}
	
	public void disableFields() {
		lastNameField.setDisable(true);
		this.firstNameField.setDisable(true);
		this.emailField.setDisable(true);
		this.phoneField.setDisable(true);
		this.birthDayField.setDisable(true);
		this.sexeField.setDisable(true);
		
	}
	
}
