package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.FormationRepository;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.dao.repositories.SessionRepository;
import com.formaplus.utils.AlertMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class SessionDialogController extends Controller implements Initializable {
	
	@FXML
	@Future(message="La date de début n'est pas valide")
	@NotNull(message="la date début est obligatoire")
	private DatePicker startDateField;
	
	@FXML
	@Future(message="La date de fin n'est pas valide")
	@NotNull(message="la date fin est obligatoire")
	private DatePicker endDateField;
	
	@FXML
	private TableView<Formation> formationsTable;
	
	@FXML
	private TableColumn<Formation, CheckBox> libColumn;
	
	@FXML
	private TableColumn<Formation, Integer> idFormaColumn;
	
	@FXML
	private Button saveButton;
	
	@FXML
	@NotEmpty(message="le libellé de la session n'est pas valide")
	
	private TextField libFormaField;
	
	private int idSession = 0;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		if(this.idSession != 0) {
			if(endDateField.getValue().isAfter(startDateField.getValue())){
				Session session = new Session();
				session.setDateDebut(startDateField.getValue());
				session.setDateFin(endDateField.getValue());
				session.setLibSession(libFormaField.getText());
				session.setIdSession(this.idSession);
				RepositoryFactory.getSessionRepository().update(session);
				AlertMessage.showInformation("Les informations de la session ont été mis jour");
			} else {
				AlertMessage.showWarning("la date de fin ne peut être inférieur à la date de début");
			}
		} else {
			ObservableList<Formation> selectedFormations = FXCollections.observableArrayList();
			for(Formation forma: formationsTable.getItems()) {
				if(forma.getCheckBoxFormation().isSelected()) {
					selectedFormations.add(forma);
				}
			}
			if(this.validate()) {
				if(!selectedFormations.isEmpty()) {
					if(endDateField.getValue().isAfter(startDateField.getValue())){
						Session session = new Session();
						session.setDateDebut(startDateField.getValue());
						session.setDateFin(endDateField.getValue());
						session.setLibSession(libFormaField.getText());
						session.setFormations(selectedFormations);
						new SessionRepository().Save(session);
					} else {
						AlertMessage.showWarning("la date de fin ne peut être inférieur à la date de début");
					}
				} else {
					AlertMessage.showWarning("Veillez selectionner les formations de la session");
					
				}
			}
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		libColumn.setCellValueFactory(new PropertyValueFactory<Formation, CheckBox>("checkBoxFormation"));
		idFormaColumn.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("idFormation"));
		formationsTable.setItems(new FormationRepository().GetAllWithCheckBox());
	}
	
	@Override
	public void setData(Object obj) {
		Session s = (Session)obj;
		libFormaField.setText(s.getLibSession());
		startDateField.setValue(s.getDateDebut());
		endDateField.setValue(s.getDateFin());
		this.idSession = s.getIdSession();
		ObservableList<Formation> formationsList = FXCollections.observableArrayList(RepositoryFactory.getFormationRepository().GetAll());
		
		for(Formation f: formationsList) {
			for(Formation sf: s.getFormations()) {
				if(f.getIdFormation() == sf.getIdFormation()) {
					CheckBox checkBox = new CheckBox(f.getLibFormation());
					checkBox.setSelected(true);
					f.setCheckBoxFormation(checkBox);
				} 
			}
			f.getCheckBoxFormation().setOnAction((ActionEvent event) -> {
				this.updateOrDeleteFormation(event, s, f);
			});
		}
		formationsTable.setItems(formationsList);
	}
	
	private void updateOrDeleteFormation(ActionEvent event, Session s, Formation f) {
		CheckBox cbx = (CheckBox)event.getSource();
		SessionRepository sessionRepo = RepositoryFactory.getSessionRepository();
		if(cbx.isSelected()) {
			if(!sessionRepo.insertFormation(s.getIdSession(), f.getIdFormation())) {
				//AlertMessage.showInformation("Cette formation a été ajouter à la session");
				cbx.setSelected(false);
			}
			/*
			if(AlertMessage.showConfirm("Voulez vous vraiment ajouter cette formation de cette session ?")) {
				
			} else {
				cbx.setSelected(false);
			}*/
		} else {
			if(!sessionRepo.hasInscription(s.getIdSession(), f.getIdFormation())) {
				if(!sessionRepo.removeFormation(s.getIdSession(), f.getIdFormation())) {
					cbx.setSelected(true);
				}
			} else {
				AlertMessage.showInformation("Vous ne pouvez supprimmer cette formation de cette session");
				cbx.setSelected(true);
			}
			/*
			if(AlertMessage.showConfirm("Voulez vous vraiment supprimer cette formation de cette session ?")) {
				if(sessionRepo.hasInscription(s.getIdSession(), f.getIdFormation())) {
					if(sessionRepo.removeFormation(s.getIdSession(), f.getIdFormation())) {
						//AlertMessage.showInformation("Cette formation a été supprimer de la session");
					} else {
						cbx.setSelected(true);
					}
				} else {
					AlertMessage.showInformation("Vous ne pouvez supprimmer cette formation de cette session");
					cbx.setSelected(true);
				}
			} else {
				cbx.setSelected(true);
			}*/
		}
	}
}
