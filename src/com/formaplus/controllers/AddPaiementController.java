package com.formaplus.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.time.LocalDate;

import com.formaplus.dao.DbConnector;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.models.Paiement;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.AlertMessage;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class AddPaiementController {
	@FXML
	private TextField idInscField;
	@FXML
	private Button searchInscButton;
	@FXML
	private TextField payMontantField;
	@FXML
	private Button saveButton;
	@FXML
	private Label nomLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label formationLabel;
	@FXML
	private Label prixFormaLabel;
	@FXML
	private Label dejaVersetLabel;
	@FXML
	private Label aVerserLabel;

	// Event Listener on Button[#searchInscButton].onAction
	@FXML
	public void handleSearchInscButtonAction(ActionEvent event) {
		try(ResultSet rset = DbConnector.executeQuery("SELECT * FROM insc_pay_status WHERE id_insc = ?", this.idInscField.getText())) {
			if(rset.next()) {
				nomLabel.setText(rset.getString("nom_etu") + " " + rset.getString("prenom_etu"));
				formationLabel.setText(rset.getString("lib_forma"));
				prixFormaLabel.setText(rset.getString("prix_forma") + " FCFA");
				dejaVersetLabel.setText(rset.getString("deja_verse") + " FCFA");
				aVerserLabel.setText(rset.getString("a_verser") + " FCFA");
			} else {
				AlertMessage.showInformation("Le numéro de l'inscription entré n'est pas valide");
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		if(!payMontantField.getText().isEmpty()) {
			Paiement pay = new Paiement();
			pay.setDatePay(LocalDate.now());
			pay.setMontantPay(Double.parseDouble(payMontantField.getText()));
			Inscription insc = new Inscription();
			insc.setIdInsc(Integer.parseInt(this.idInscField.getText()));
			pay.setInscription(insc);
			if(AlertMessage.showConfirm("Voulez vous enrégistrer le paiement ?")) {
				int idPay = RepositoryFactory.getPaiementRepository().insert(pay);
				if(idPay > 0) {
					AlertMessage.showInformation("Paiement effectif");
				} else AlertMessage.showInformation("Paiement non effectif");
			}
		} else AlertMessage.showInformation("Veillez entrer le montant");
		
	}
}
