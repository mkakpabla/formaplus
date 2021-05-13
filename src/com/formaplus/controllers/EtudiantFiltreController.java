package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.JasperViewerFX;
import com.formaplus.utils.Reporting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EtudiantFiltreController implements Initializable {
	@FXML
	private ComboBox<Session> sessionField;
	@FXML
	private ComboBox<Formation> formationField;
	@FXML
	private Button printButton;

	// Event Listener on ComboBox[#sessionField].onAction
	@FXML
	public void handleSessionFieldAction(ActionEvent event) {
		formationField.setItems(FXCollections.observableArrayList(RepositoryFactory.getFormationRepository().GetAllWhereSessionId(sessionField.getValue().getIdSession())));
	}
	// Event Listener on Button[#printButton].onAction
	@FXML
	public void handlePrintButtonAction(ActionEvent event) {
		ObservableList<Etudiant> etudiantsListe = FXCollections.observableArrayList();
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(sessionField.getValue() != null) {
			
			if(formationField.getValue() != null) {
				etudiantsListe = RepositoryFactory.getEtudiantRepository().getAllWhereSessionAndFormation(sessionField.getValue().getIdSession(), formationField.getValue().getIdFormation());
				parameters.put("FORMATION", formationField.getValue().getLibFormation());
				parameters.put("SESSION", sessionField.getValue().getLibSession());
			} else {
				etudiantsListe = RepositoryFactory.getEtudiantRepository().getAllWhereSession(sessionField.getValue());
				parameters.put("SESSION", sessionField.getValue().getLibSession());
			}
			/*
			 * JRDesignQuery query = new JRDesignQuery();
			 * 
			 * query.setText("SELECT * FROM etudiants"); jasperDesign.setQuery(query);
			 */
		} else {
			etudiantsListe = RepositoryFactory.getEtudiantRepository().GetAll();
		}
		
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(etudiantsListe);

		parameters.put("TABLE_DATA", itemsJRBean);
		parameters.put("ADDRESS", "Agoé-logopé");
		parameters.put("PHONE", "+228 98647306");
		JasperPrint jasperPrint = Reporting.getJasperPrint("etudiants.jrxml", parameters);
		JasperViewerFX view = new JasperViewerFX();
		view.viewReport("Liste des etudiants", jasperPrint, printButton);
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		sessionField.setItems(FXCollections.observableArrayList(RepositoryFactory.getSessionRepository().GetAll()));
	}
}
