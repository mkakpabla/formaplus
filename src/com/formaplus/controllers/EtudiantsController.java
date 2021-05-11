package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.repositories.EtudiantRepository;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;

import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class EtudiantsController implements Initializable {
	@FXML
	private TableView<Etudiant> etudiantsTable;
	@FXML
	private TableColumn<Etudiant, Integer> idEtuColumn;
	@FXML
	private TableColumn<Etudiant, String> nomEtuColumn;
	@FXML
	private TableColumn<Etudiant, String> prenomEtuColumn;
	@FXML
	private TableColumn<Etudiant, String> sexeEtuColumn;
	@FXML
	private TableColumn<Etudiant, String> emailEtuColumn;
	@FXML
	private TableColumn<Etudiant, Integer> telEtuColumn;
	@FXML
	private TextField searchField;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
    private Button printButton;
	

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void handleEditButtonAction(ActionEvent event) {
		Etudiant e = etudiantsTable.getSelectionModel().getSelectedItem();
		if(e != null) {
			Stage stage = LoadView.getModalStage(deleteButton, "EtudiantEdit", e, "Editer un �tudiant");
			stage.setOnCloseRequest(evt ->loadEtudiants());
			stage.showAndWait();
		} else {
			AlertMessage.showInformation("Veillez selectionner un �tudiant");
		}
		
	}
	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void handleDeleteButtonAction(ActionEvent event) {
		EtudiantRepository etudiantRepository = RepositoryFactory.getEtudiantRepository();
		Etudiant etudiant = etudiantsTable.getSelectionModel().getSelectedItem();
		if(etudiant != null) {
			if(AlertMessage.showConfirm("Voulez vous vraimment supprimer cet �tudiant ? Cette peut entra�ner des op�ration ir�versible.")) {
				if(!etudiantRepository.hasInscription(etudiant.getIdEtu())) {
					if(etudiantRepository.Delete(etudiant.getIdEtu())) {
						AlertMessage.showInformation("L'�tudiant � �t� supprimer");
						this.loadEtudiants();
					}
				} else {
					AlertMessage.showInformation("Cet �tudiant est d�ja inscrit dans une formation.");
					
				}
				
			}
		} else {
			AlertMessage.showInformation("Veillez selectionner un �tudiant");
		}
		
	}
	
	@FXML
    public void handlePrintButtonAction(ActionEvent event) {
		LoadView.showModal(printButton.getScene().getWindow(), "EtudiantFiltre", "Imprimer liste �tudiant");
		

    }
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.initTable();
		this.loadEtudiants();
	}
	
	private void loadEtudiants() {
		etudiantsTable.setItems(new EtudiantRepository().GetAll());
	}
	
	public void initTable() {
		idEtuColumn.setCellValueFactory(cell -> cell.getValue().idEtuProperty().asObject());
		nomEtuColumn.setCellValueFactory(cell -> cell.getValue().nomEtuProperty());
		prenomEtuColumn.setCellValueFactory(cell -> cell.getValue().prenomEtuProperty());
		sexeEtuColumn.setCellValueFactory(cell -> cell.getValue().sexeEtuProperty());
		emailEtuColumn.setCellValueFactory(cell -> cell.getValue().emailEtuProperty());
		telEtuColumn.setCellValueFactory(cell -> cell.getValue().telEtuProperty().asObject());
	}
}
