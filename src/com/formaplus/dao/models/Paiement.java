package com.formaplus.dao.models;

import java.util.Date;

public class Paiement {
	
	private int idPay;
	private Date datePay;
	private double montantPay;
	private Etudiant etudiant;
	private Paiement paiement;
	
	
	public int getIdPay() {
		return idPay;
	}
	public void setIdPay(int idPay) {
		this.idPay = idPay;
	}
	public Date getDatePay() {
		return datePay;
	}
	public void setDatePay(Date datePay) {
		this.datePay = datePay;
	}
	public double getMontantPay() {
		return montantPay;
	}
	public void setMontantPay(double montantPay) {
		this.montantPay = montantPay;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Paiement getPaiement() {
		return paiement;
	}
	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}
	
	
	

}
