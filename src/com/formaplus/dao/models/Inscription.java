package com.formaplus.dao.models;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Inscription {
	
	private IntegerProperty idInsc;
	private LocalDate dateInsc;
	private DoubleProperty prixInsc;
	private ObjectProperty<Etudiant> etudiant;
	private ObjectProperty<Session> session;
	private ObjectProperty<Formation> formation;
	
	public Inscription() {
		this.idInsc = new SimpleIntegerProperty();
		this.prixInsc = new SimpleDoubleProperty();
		this.etudiant = new SimpleObjectProperty<Etudiant>();
		this.session = new SimpleObjectProperty<Session>();
		this.formation = new SimpleObjectProperty<Formation>();
	}
	
	public IntegerProperty idInscProperty() {
		return idInsc;
	}
	public void setIdInsc(int idInsc) {
		this.idInsc.set(idInsc);
	}
	public LocalDate getDateInsc() {
		return dateInsc;
	}
	public void setDateInsc(LocalDate dateInsc) {
		this.dateInsc = dateInsc;
	}
	public DoubleProperty prixInscProperty() {
		return prixInsc;
	}
	public void setPrixInsc(double prixInsc) {
		this.prixInsc.set(prixInsc);
	}
	public ObjectProperty<Etudiant> etudiantProperty() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant.set(etudiant);
	}
	public ObjectProperty<Session> sessionProperty() {
		return session;
	}
	public void setSession(Session session) {
		this.session.set(session);
	}
	public ObjectProperty<Formation> formationProperty() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation.set(formation);
	}
	
	// Getters
	public int getIdInsc() {
		return idInsc.get();
	}
	public double getPrixInsc() {
		return prixInsc.get();
	}
	public Etudiant getEtudiant() {
		return etudiant.get();
	}
	public Session getSession() {
		return session.get();
	}
	public Formation getFormation() {
		return formation.get();
	}
	
	
	
	
	
	
	
	
	
	
	

}
