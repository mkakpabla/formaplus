package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.repositories.InscriptionRepository;
import com.formaplus.utils.LoadView;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InscriptionsController implements Initializable {
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private AnchorPane mainAnchorePane;
	
	
	@FXML
    private TableView<Inscription> inscTable;
	
	@FXML
	private TableColumn<Inscription, Integer> idInscColumn;
	@FXML
	private TableColumn<Inscription, Etudiant> etudiantColumn;
	@FXML
	private TableColumn<Inscription, Formation> formationColumn;
	@FXML
	private TableColumn<Inscription, String> dateInscColumn;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button printButton;
	@FXML
	private TextField searchField;
	@FXML
	private Button newInscButton;

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void handleEditButtonAction(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void handleDeleteButtonAction(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#printButton].onAction
	@FXML
	public void handlePrintButtonAction(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#newInscButton].onAction
	@FXML
	public void handlenewInscButtonAction(ActionEvent event) {
		Stage dialogStage = LoadView.getModalStage(newInscButton, "InscriptionDialog", "Enrégistrer une nouvelle inscription");
		dialogStage.setOnCloseRequest(evt -> loadInscriptions());
		dialogStage.showAndWait();
		
	}
	
	private void loadInscriptions() {
		
	}
	
	private void initTable() {
		formationColumn.setCellValueFactory(cell -> cell.getValue().formationProperty());
		etudiantColumn.setCellValueFactory(cell -> cell.getValue().etudiantProperty());
		dateInscColumn.setCellValueFactory(new PropertyValueFactory<Inscription, String>("dateInsc"));
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTable();
		inscTable.setItems(new InscriptionRepository().GetAll());
	}
}
