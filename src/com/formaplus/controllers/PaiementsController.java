package com.formaplus.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import com.formaplus.dao.models.Paiement;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class PaiementsController {
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane mainAnchorePane;
	@FXML
	private TableView<Paiement> payTable;
	@FXML
	private TableColumn<Paiement, Integer> idPay;
	@FXML
	private TableColumn<Paiement, String> etudiantColumn;
	@FXML
	private TableColumn<Paiement, String> formationColumn;
	@FXML
	private TableColumn<Paiement, String> montantPay;
	@FXML
	private TableColumn<Paiement, String> datePay;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button printButton;
	@FXML
	private Button addButton;

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
	// Event Listener on Button[#addButton].onAction
	@FXML
	public void handlenewInscButtonAction(ActionEvent event) {
		// TODO Autogenerated
	}
}