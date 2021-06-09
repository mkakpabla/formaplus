package com.formaplus.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.formaplus.dao.DbConnector;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.models.Paiement;
import com.formaplus.dao.repositories.RepositoryFactory;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.JasperViewerFX;
import com.formaplus.utils.Reporting;

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
	
	private double aVerser;

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
				this.aVerser = rset.getDouble("a_verser");
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
			if(aVerser < Double.valueOf(payMontantField.getText())) {
				AlertMessage.showInformation("Le montant entré est supérieur au montant restant");
				return;
			}
			Paiement pay = new Paiement();
			pay.setDatePay(LocalDate.now());
			pay.setMontantPay(Double.parseDouble(payMontantField.getText()));
			Inscription insc = new Inscription();
			insc.setIdInsc(Integer.parseInt(this.idInscField.getText()));
			pay.setInscription(insc);
			
			if(AlertMessage.showConfirm("Voulez vous enrégistrer le paiement ?")) {
				int idPay = RepositoryFactory.getPaiementRepository().insert(pay);
				if(idPay > 0) {
					try(ResultSet rset = DbConnector.executeQuery("SELECT * FROM insc_pay_status WHERE id_insc = ?", this.idInscField.getText())) {
						if(rset.next()) {
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("E_NAME", rset.getString("nom_etu") + " " + rset.getString("prenom_etu"));
							parameters.put("E_EMAIL", rset.getString("email_etu"));
							parameters.put("E_PHONE", rset.getString("tel_etu"));
							parameters.put("F_SESSION", rset.getString("lib_session"));
							parameters.put("F_NAME", rset.getString("lib_forma"));
							parameters.put("F_PRIX", rset.getString("prix_forma"));
							parameters.put("P_NUM", rset.getString("id_pay"));
							parameters.put("P_MONTANT", rset.getString("montant_pay"));
							parameters.put("P_DATE", rset.getString("date_pay"));
							parameters.put("P_RESTE", rset.getString("a_verser"));
							parameters.put("P_VERSE", rset.getString("deja_verse"));
							// Génération du raport
							JasperPrint jasperPrint =  Reporting.getJasperPrint("fiche_paiement.jrxml", parameters);
							JasperViewerFX view = new JasperViewerFX();
							view.viewReport("Fiche de paiement", jasperPrint, searchInscButton);
						} else {
							AlertMessage.showInformation("Le numéro de l'inscription entré n'est pas valide");
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					AlertMessage.showInformation("Paiement effectif");
				} else AlertMessage.showInformation("Paiement non effectif");
			}
		} else AlertMessage.showInformation("Veillez entrer le montant");
		
	}
}
