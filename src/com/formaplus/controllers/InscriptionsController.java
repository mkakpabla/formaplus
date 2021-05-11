package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.repositories.InscriptionRepository;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;
import com.formaplus.utils.Reporting;

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
		Inscription insc = inscTable.getSelectionModel().getSelectedItem();
		if (insc != null) {
			Stage dialogStage = LoadView.getModalStage(editButton, "InscriptionDialog", insc, "Editer l'inscription");
			dialogStage.setOnCloseRequest(evt -> loadInscriptions());
			dialogStage.showAndWait();
		} else {
			AlertMessage.showInformation("Veillez selectionner une inscription");
		}
	}

	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void handleDeleteButtonAction(ActionEvent event) {
		Inscription insc = this.inscTable.getSelectionModel().getSelectedItem();
		if(insc != null) {
			if(!RepositoryFactory.getInscriptionRepository().hasPaiement(insc.getIdInsc())) {
				if(AlertMessage.showConfirm("Voulez vous vraiment supprimer cette inscription ?")) {
					if(RepositoryFactory.getInscriptionRepository().Delete(insc.getIdInsc())) {
						AlertMessage.showInformation("Cette inscription a été supprimer avce succes");
						this.loadInscriptions();
					}
				}
			} else {
				AlertMessage.showInformation("Vous ne pouvez supprimer cette inscription. Des paiements ont été enrégistrer");
			}
			
		} else AlertMessage.showInformation("Veillez selectionner une inscription");
	
	}

	// Event Listener on Button[#printButton].onAction
	@FXML
	public void handlePrintButtonAction(ActionEvent event) {
		Inscription insc = this.inscTable.getSelectionModel().getSelectedItem();
		if(insc != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("adr", "Agoé-Logopé");
			parameters.put("tel", "+228 98647306");
			parameters.put("mat", insc.getEtudiant().getIdEtu());
			parameters.put("name", insc.getEtudiant().toString());
			parameters.put("prix_forma", insc.getFormation().getPrixFormation());
			parameters.put("prix_insc", insc.getPrixInsc());
			parameters.put("date_insc", insc.getDateInsc().toString());
			parameters.put("formation", insc.getFormation().getLibFormation() + " " + insc.getSession().getLibSession());
			parameters.put("insc_num", insc.getFullNum());
			// Génération du raport
			Reporting.showReport("fiche_inscription.jrxml", parameters);
		} else AlertMessage.showInformation("Veillez selectionner une inscription");
		
		
		
	}

	// Event Listener on Button[#newInscButton].onAction
	@FXML
	public void handlenewInscButtonAction(ActionEvent event) {
		Stage dialogStage = LoadView.getModalStage(newInscButton, "InscriptionDialog",
				"Enrégistrer une nouvelle inscription");
		dialogStage.setOnCloseRequest(evt -> loadInscriptions());
		dialogStage.showAndWait();

	}

	private void loadInscriptions() {
		inscTable.setItems(RepositoryFactory.getInscriptionRepository().GetAll());
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
