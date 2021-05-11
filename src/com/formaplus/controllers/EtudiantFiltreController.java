package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Session;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.AlertMessage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
		if(sessionField.getValue() != null) {
			try {
				JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/com/formaplus/resources/reports/etudiants.jrxml"));
				/*
				 * JRDesignQuery query = new JRDesignQuery();
				 * 
				 * query.setText("SELECT * FROM etudiants"); jasperDesign.setQuery(query);
				 */
				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(RepositoryFactory.getEtudiantRepository().getAllWhereSession(sessionField.getValue()));

				/* Map to hold Jasper report Parameters */
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("TABLE_DATA", itemsJRBean);
				parameters.put("ADDRESS", "Agoé-logopé");
				parameters.put("PHONE", "+228 98647306");
				parameters.put("SESSION", sessionField.getValue().getLibSession());
				
				JasperReport jaspertReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jaspertReport, parameters, new JREmptyDataSource());
				JasperViewer.viewReport(jasperPrint, false);
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			AlertMessage.showInformation("Veillez selectionner une session.");
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		sessionField.setItems(FXCollections.observableArrayList(RepositoryFactory.getSessionRepository().GetAll()));
	}
}
