package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.EtudiantRepository;
import com.formaplus.dao.repositories.FormationRepository;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.dao.repositories.SessionRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.Db;
import com.formaplus.utils.LoadView;
import com.formaplus.utils.Reporting;
import com.formaplus.utils.Utils;
import com.formaplus.utils.Validator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;

public class InscriptionDialogController extends Controller implements Initializable {
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
	
	private InputStream choosedPhoto;
	
	private int idInsc = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initComboBox();
		this.sexeField.getSelectionModel().select(0);
	}

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		if(this.idInsc == 0) {
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
			
			if(this.idField.getText().isEmpty()) {
				if(validator.isValid()) {
					
					if(choosedPhoto != null) {
						Etudiant etudiant = new Etudiant();
						etudiant.setNomEtu(lastNameField.getText());
						etudiant.setPrenomEtu(firstNameField.getText());
						etudiant.setEmailEtu(this.emailField.getText());
						etudiant.setDateAjout(LocalDate.now());
						etudiant.setTelEtu(Integer.parseInt(this.phoneField.getText()));
						etudiant.setSexeEtu(sexeField.getValue());
						etudiant.setPhotoEtu(choosedPhoto);
						etudiant.setDateNaissEtu(birthDayField.getValue());
						
						Formation formation = formationField.getSelectionModel().getSelectedItem();
						Session session = sessionField.getSelectionModel().getSelectedItem();
						
						Inscription insc = new Inscription();
						insc.setEtudiant(etudiant);
						insc.setFormation(formation);
						insc.setSession(session);
						insc.setPrixInsc(Double.parseDouble(priceField.getText()));
						insc.setDateInsc(LocalDate.now());
						
						int idInsc = RepositoryFactory.getInscriptionRepository().insertWithNewStudent(insc);
						System.out.println(idInsc);
						if(idInsc != 0) {
							AlertMessage.showInformation("Inscription enrégistrer avec succes");
							insc = RepositoryFactory.getInscriptionRepository().GetById(idInsc);
							this.printFicheInsc(insc);
						}
					} else AlertMessage.showWarning("La photo de l'étudiant est requis");
					
				} else {
					List<String> errors = validator.getErrors();
					AlertMessage.showWarning(errors.get(0));
				}
			} else {
				Etudiant etudiant = RepositoryFactory.getEtudiantRepository().GetById(Integer.parseInt(idField.getText()));
				if(etudiant != null) {
					Formation formation = formationField.getSelectionModel().getSelectedItem();
					Session session = sessionField.getSelectionModel().getSelectedItem();
					Inscription insc = new Inscription();
					insc.setEtudiant(etudiant);
					insc.setFormation(formation);
					insc.setSession(session);
					insc.setPrixInsc(Double.parseDouble(priceField.getText()));
					insc.setDateInsc(LocalDate.now());
					
					try(PreparedStatement p = Db.getConnection().prepareStatement("SELECT * FROM inscriptions WHERE id_forma = ? AND id_session = ? AND id_etu = ?")) {
						p.setInt(1, formation.getIdFormation());
						p.setInt(2, session.getIdSession());
						p.setInt(3, etudiant.getIdEtu());
						ResultSet rset = p.executeQuery();
						if(rset.next()) {
							AlertMessage.showInformation(etudiant.toString() + " est déja inscrit dans cette formation pour cette section");
						} else {
							int idInsc = RepositoryFactory.getInscriptionRepository().insert(insc);
							insc = RepositoryFactory.getInscriptionRepository().GetById(idInsc);
							this.printFicheInsc(insc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					AlertMessage.showInformation("Etudiant non trouvé");
				}
				
			}
		} else {
			Etudiant etudiant = new Etudiant();
			etudiant.setIdEtu(Integer.parseInt(idField.getText()));
			Inscription insc = new Inscription();
			insc.setEtudiant(etudiant);
			insc.setIdInsc(this.idInsc);
			insc.setFormation(formationField.getSelectionModel().getSelectedItem());
			insc.setSession(sessionField.getSelectionModel().getSelectedItem());
			insc.setPrixInsc(Double.parseDouble(priceField.getText()));
			if(RepositoryFactory.getInscriptionRepository().update(insc)) {
				AlertMessage.showInformation("Les informations de l'inscription ont été mis à jour");
				insc = RepositoryFactory.getInscriptionRepository().GetById(idInsc);
				this.printFicheInsc(insc);
			}
		}
		
		
		
	}
	// Event Listener on Button[#searchButton].onAction
	@FXML
	public void handleSearchButtonAction(ActionEvent event) {
		try {
			if(!this.idField.getText().isEmpty()) {
				Etudiant etudiant = new EtudiantRepository().GetById(Integer.parseInt(idField.getText()));
				if(etudiant != null) {
					this.lastNameField.setText(etudiant.getNomEtu());
					this.firstNameField.setText(etudiant.getPrenomEtu());
					this.emailField.setText(etudiant.getEmailEtu());
					this.phoneField.setText(String.valueOf(etudiant.getTelEtu()));
					this.birthDayField.setValue(etudiant.getDateNaissEtu());
					this.sexeField.getSelectionModel().select(etudiant.getSexeEtu());
					photoImageView.setImage(Utils.getImage(etudiant.getPhotoEtu()));
					this.disableFields();
				} else {
					AlertMessage.showInformation("Aucun étudiant n'a été trouvé");
				}
			} else AlertMessage.showInformation("Le champ identifient est obligatoire");
		} catch (NumberFormatException e) {
			AlertMessage.showInformation("L'identifient n'est pas valide");
			
		}
		
		
	}
	// Event Listener on Button[#imageChooseButton].onAction
	@FXML
	public void handleImageChooseButton(ActionEvent event) {
		File file = LoadView.chooseImageDialog(imageChooseButton);
		if(file != null) {
			try {
				photoImageView.setImage(new Image(file.toURI().toString(), 140, 130, true, true));
				choosedPhoto = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
    public void handlleSessionFieldAction(ActionEvent event) {
		this.setFormationField(); 
    }
	
	
	public void setData(Object obj) {
		Inscription insc = (Inscription)obj;
		idInsc = insc.getIdInsc();
		Etudiant etudiant = insc.getEtudiant();
		this.lastNameField.setText(etudiant.getNomEtu());
		this.firstNameField.setText(etudiant.getPrenomEtu());
		this.emailField.setText(etudiant.getEmailEtu());
		this.phoneField.setText(String.valueOf(etudiant.getTelEtu()));
		this.birthDayField.setValue(etudiant.getDateNaissEtu());
		this.sexeField.getSelectionModel().select(etudiant.getSexeEtu());
		this.priceField.setText(String.valueOf(insc.getPrixInsc()));
		this.sessionField.getSelectionModel().select(insc.getSession());
		this.setFormationField();
		this.formationField.getSelectionModel().select(insc.getFormation());
		this.idField.setText(String.valueOf(etudiant.getIdEtu()));
		this.photoImageView.setImage(Utils.getImage(etudiant.getPhotoEtu()));
		
		this.disableFields();
	}
	
	
	private void setFormationField() {
		Session s = sessionField.getSelectionModel().getSelectedItem();
		formationField.setItems(new FormationRepository().GetAllWhereSessionId(s.getIdSession()));
	}
	
	private void initComboBox() {
		List<Session> sessions = new SessionRepository().GetAll().stream()
											.filter(s -> s.getDateFin().isAfter(LocalDate.now())).collect(Collectors.toList());
		sessionField.setItems(FXCollections.observableArrayList(sessions));
		List<String> sexe = new ArrayList<String>();
		sexe.add("Masculin");
		sexe.add("Féminin");
		sexeField.setItems(FXCollections.observableArrayList(sexe));
		
	}
	
	private void disableFields() {
		lastNameField.setDisable(true);
		this.firstNameField.setDisable(true);
		this.emailField.setDisable(true);
		this.phoneField.setDisable(true);
		this.birthDayField.setDisable(true);
		this.sexeField.setDisable(true);
		this.imageChooseButton.setDisable(true);
	}
	
	
	private void printFicheInsc(Inscription insc) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("adr", "Agoé-Logopé");
		parameters.put("tel", "+228 98647306");
		parameters.put("mat", insc.getEtudiant().getIdEtu());
		parameters.put("name", insc.getEtudiant().toString());
		parameters.put("prix_forma", insc.getFormation().getPrixFormation());
		parameters.put("prix_insc", insc.getPrixInsc());
		parameters.put("date_insc", insc.getDateInsc().toString());
		parameters.put("formation", insc.getFormation().getLibFormation() + " " + insc.getSession().getLibSession());
		
		// Génération du raport
		Reporting.showReport("fiche_inscription.jrxml", parameters);
	}
	
}
