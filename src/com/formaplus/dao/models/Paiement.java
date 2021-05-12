package com.formaplus.dao.models;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Paiement {
	
	private IntegerProperty idPay;
	private LocalDate datePay;
	private DoubleProperty montantPay;
	private ObjectProperty<Inscription> inscription;
	private ObjectProperty<Etudiant> etudiant;
	
	
	
	
	public Paiement() {
		this.idPay = new SimpleIntegerProperty();
		this.datePay = LocalDate.now();
		this.montantPay = new SimpleDoubleProperty();
		this.inscription = new SimpleObjectProperty<Inscription>();
		this.etudiant = new SimpleObjectProperty<Etudiant>();
	}




	/**
	 * @return the idPayProperty for JavaFx
	 */
	public IntegerProperty idPayProperty() {
		return idPay;
	}
	
	/**
	 * @return the idPay Value
	 */
	public int getIdPay() {
		return idPay.get();
	}




	/**
	 * @param idPay the idPay to set
	 */
	public void setIdPay(int idPay) {
		this.idPay.set(idPay);
	}




	/**
	 * @return the datePay
	 */
	public LocalDate getDatePay() {
		return datePay;
	}




	/**
	 * @param datePay the datePay to set
	 */
	public void setDatePay(LocalDate datePay) {
		this.datePay = datePay;
	}




	/**
	 * @return the montantPayProperty for JavaFx
	 */
	public DoubleProperty montantPayProperty() {
		return montantPay;
	}
	
	/**
	 * @return the montantPay
	 */
	public double getMontantPay() {
		return montantPay.get();
	}




	/**
	 * @param montantPay the montantPay to set
	 */
	public void setMontantPay(double montantPay) {
		this.montantPay.set(montantPay);
	}




	/**
	 * @return the inscription
	 */
	public ObjectProperty<Inscription> inscriptionProperty() {
		return inscription;
	}
	
	/**
	 * @return the inscription
	 */
	public Inscription getInscription() {
		return inscription.get();
	}




	/**
	 * @param inscription the inscription to set
	 */
	public void setInscription(Inscription inscription) {
		this.inscription.set(inscription);
	}




	/**
	 * @return the etudiant
	 */
	public ObjectProperty<Etudiant> etudiantProperty() {
		return etudiant;
	}
	
	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant.get();
	}




	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant.set(etudiant);
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
