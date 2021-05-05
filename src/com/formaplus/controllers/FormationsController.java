package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Formation;
import com.formaplus.dao.repositories.FormationRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class FormationsController implements Initializable {
	@FXML
	private TableView<Formation> formationsTable;
	@FXML
	private TableColumn<Formation, Integer> idFormationColumn;
	@FXML
	private TableColumn<Formation, String> libFormationColumn;
	@FXML
	private TableColumn<Formation, Double> prixFormationColumn;
	@FXML
	private TableColumn<Formation, Integer> dureeFormationColumn;
	@FXML
	private TextField searchField;
	@FXML
	private Button addFormationButton;
	@FXML
	private Button updateFormationButton;
	@FXML
	private Button deleteFormationButton;
	
	private ObservableList<Formation> formationsList;
	
	private FormationRepository repository = new FormationRepository();

	// Event Listener on Button[#addFormationButton].onAction
	@FXML
	public void handleAddFormationButtonAction(ActionEvent event) {
		Stage dialogStage = LoadView.getModalStage(addFormationButton, "FormationDialog", "Ajouter une nouvelle foration");
		dialogStage.setOnCloseRequest(evt -> loadFormations());
		dialogStage.showAndWait();
		
	}
	// Event Listener on Button[#updateFormationButton].onAction
	@FXML
	public void handleUpdateButtonAction(ActionEvent event) {
		Formation formation =  formationsTable.getSelectionModel().getSelectedItem();
		if(formation != null) {
			Stage dialogStage = LoadView.getModalStage(addFormationButton, "FormationDialog", formation, "Editer la formation");
			dialogStage.setOnCloseRequest(evt -> loadFormations());
			dialogStage.showAndWait();
		} else {
			AlertMessage.showInformation("Information", "Veillez selectionner une formation");
		}
	}
	// Event Listener on Button[#deleteFormationButton].onAction
	@FXML
	public void handleDeleteButtonAction(ActionEvent event) {
		Formation formation =  formationsTable.getSelectionModel().getSelectedItem();
		if(formation != null) {
			ButtonType result = AlertMessage.showConfirm("Suppression", "Voulez vous vraimment supprimer cette formation ? Cette peut entra�ner des op�ration ir�versible.");
			if(result == ButtonType.OK) {
				if(repository.Delete(formation.getIdFormation())) {
					AlertMessage.showInformation("Formation supprim�e");
					this.loadFormations();
				}
			}
			
		} else {
			AlertMessage.showInformation("Veillez selectionner une formation");
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.idFormationColumn.setCellValueFactory(cellData -> cellData.getValue().idFormationProperty().asObject());
		this.libFormationColumn.setCellValueFactory(cellData -> cellData.getValue().libFormationProperty());
		this.prixFormationColumn.setCellValueFactory(cellData -> cellData.getValue().prixFormationProperty().asObject());
		this.dureeFormationColumn.setCellValueFactory(cellData -> cellData.getValue().dureeFormationProperty().asObject());
		loadFormations();
	}
	
	
	private void loadFormations() {
		formationsList = FXCollections.observableArrayList(this.repository.GetAll());
		formationsTable.setItems(formationsList);
		
	}
}
