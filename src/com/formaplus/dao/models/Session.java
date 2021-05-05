package com.formaplus.dao.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;



public class Session {
	
	private Integer idSession;
	private String libSession;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private ObservableList<Formation> formations;
	private ObservableList<Inscription> inscriptions;
	
	
	public Integer getIdSession() {
		return idSession;
	}
	public void setIdSession(Integer idSession) {
		this.idSession = idSession;
	}
	public String getLibSession() {
		return libSession;
	}
	public void setLibSession(String libSession) {
		this.libSession = libSession;
	}
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDate getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	
	
	public IntegerProperty idSessionProperty() {
		return new SimpleIntegerProperty(this.idSession);
	}
	
	public StringProperty libSessionProperty() {
		return new SimpleStringProperty(this.libSession);
	}
	public ObservableList<Formation> getFormations() {
		return formations;
	}
	public void setFormations(ObservableList<Formation> formations) {
		this.formations = formations;
	}
	public ObservableList<Inscription> getInscriptions() {
		return inscriptions;
	}
	public void setInscriptions(ObservableList<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
	
	
	@Override
	public String toString() {
		return libSession;
		
	}
	
	
	
	

}
