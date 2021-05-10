package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.dao.repositories.SessionRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.LoadView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SessionsController implements Initializable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane mainAnchorePane;
	
	@FXML
    private TableView<Session> sessionsTable;
	
	@FXML
	private TableColumn<Session, Integer> idColumn;
	@FXML
	private TableColumn<Session, String> libColumn;
	@FXML
	private TableColumn<Session, LocalDate> startDateColumn;
	@FXML
	private TableColumn<Session, LocalDate> endDateColumn;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TextField searchField;
	@FXML
	private Button newSessionButton;

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void handleEditButtonAction(ActionEvent event) {
		Session session = sessionsTable.getSelectionModel().getSelectedItem();
		Stage dialogStage = LoadView.getModalStage(newSessionButton, "SessionDialog", session, "Enrégistrer une nouvelle session");
		dialogStage.setOnCloseRequest(evt -> {
			
			try {
				this.loadSessions();
				//DbConnector.getTransactionConnection().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		dialogStage.showAndWait();
	}
	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void handleDeleteButtonAction(ActionEvent event) {
		
		Session session = sessionsTable.getSelectionModel().getSelectedItem();
		if(session != null) {
			if(AlertMessage.showConfirm("Voulez vous vraimment supprimer cette session")) {
				if(!RepositoryFactory.getSessionRepository().hasInscription(session.getIdSession())) {
					if(RepositoryFactory.getSessionRepository().Delete(session.getIdSession())) {
						AlertMessage.showConfirm("La session a été supprimer");
						this.loadSessions();
					}
				} else {
					AlertMessage.showInformation("Vous ne pouvez supprimer cette session");
				}
			}
		} else {
			AlertMessage.showInformation("Veillez selectionner une session");
		}
		
	}
	// Event Listener on Button[#newSessionButton].onAction
	@FXML
	public void handleNewSessionButtonAction(ActionEvent event) {
		Stage dialogStage = LoadView.getModalStage(newSessionButton, "SessionDialog", "Enrégistrer une nouvelle session");
		//dialogStage.setOnCloseRequest(evt -> loadInscriptions());
		dialogStage.showAndWait();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		libColumn.setCellValueFactory(cellData -> cellData.getValue().libSessionProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idSessionProperty().asObject());
		startDateColumn.setCellValueFactory(new PropertyValueFactory<Session, LocalDate>("dateDebut"));
		endDateColumn.setCellValueFactory(new PropertyValueFactory<Session, LocalDate>("dateFin"));
		this.loadSessions();
		
	}
	
	public void loadSessions() {
		sessionsTable.setItems(FXCollections.observableArrayList(new SessionRepository().GetAll()));
	}
}
