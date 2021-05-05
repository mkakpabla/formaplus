package com.formaplus.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.formaplus.dao.models.Utilisateur;
import com.formaplus.dao.repositories.UtilisateurRepository;
import com.formaplus.utils.AlertMessage;
import com.formaplus.utils.Validator;

import javafx.event.ActionEvent;

public class FormUtilisateursController extends Controller implements Initializable {
	private int idUtr = 0;
	@FXML
	@NotEmpty(message = "Le nom ne peut être vide")
	private TextField txtName;
	@FXML
	@NotEmpty(message = "L'identifient ne peut être vide")
	private TextField txtLogin;
	@FXML
	@NotEmpty(message = "Le mot de passe ne peut être vide")
	@Min(value = 7, message = "Le mot de passe doit contenir au moins 7 caractères")
	private PasswordField txtPassword;
	@FXML
	private Button btnSave;

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void handleSaveUtilisateurs(ActionEvent event) {
		Validator validator = new Validator();
		validator.isAlpha(txtName.getText(), "Le nom est obligoire")
				.isAlphaNum(txtLogin.getText(), "L'identifient est obligatoire")
				.notEmpty(txtPassword.getText(), "Le mot de passe est obligatoire");
		
		if(validator.isValid()) {
			UtilisateurRepository repo = new UtilisateurRepository();
			Utilisateur u = new Utilisateur();
			u.setIdUtr(idUtr);
			u.setLoginUtr(txtLogin.getText());
			u.setNomCompUtr(txtName.getText());
			u.setMdpUtr(txtPassword.getText());
			
			if(repo.Save(u)) {
				AlertMessage.showInformation("Opération réussie", "L'utilisateur a été sauvegarder avec success");
				txtLogin.clear();
				txtName.clear();
				txtPassword.clear();
			}
		} else {
			AlertMessage.showWarning(validator.getErrors().get(0));
			
		}
		
	}
	
	@Override
	public void setData(Object obj) {
		Utilisateur utilisateur = (Utilisateur)obj;
		txtName.setText(utilisateur.getNomCompUtr());
		txtLogin.setText(utilisateur.getLoginUtr());
		txtPassword.setText(utilisateur.getMdpUtr());
		idUtr = utilisateur.getIdUtr();
		
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
