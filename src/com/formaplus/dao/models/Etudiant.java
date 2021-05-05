package com.formaplus.dao.models;

import java.io.InputStream;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Etudiant {
	
	private IntegerProperty idEtu;
	private StringProperty nomEtu;
	private StringProperty prenomEtu;
	private StringProperty emailEtu;
	private IntegerProperty telEtu;
	private LocalDate dateNaissEtu;
	private StringProperty sexeEtu;
	private InputStream photoEtu;
	private LocalDate dateAjout;
	private ListProperty<Inscription> inscriptions;
	
	
	public Etudiant() {
		this.idEtu = new SimpleIntegerProperty();
		this.nomEtu = new SimpleStringProperty();
		this.prenomEtu = new SimpleStringProperty();
		this.emailEtu = new SimpleStringProperty();
		this.telEtu = new SimpleIntegerProperty();
		this.sexeEtu = new SimpleStringProperty();
		this.inscriptions = new SimpleListProperty<Inscription>();
	}


	public IntegerProperty idEtuProperty() {
		return idEtu;
	}


	public void setIdEtu(int idEtu) {
		this.idEtu.set(idEtu);
	}


	public StringProperty nomEtuProperty() {
		return nomEtu;
	}


	public void setNomEtu(String nomEtu) {
		this.nomEtu.set(nomEtu);
	}


	public StringProperty prenomEtuProperty() {
		return prenomEtu;
	}


	public void setPrenomEtu(String prenomEtu) {
		this.prenomEtu.set(prenomEtu);
	}


	public StringProperty emailEtuProperty() {
		return emailEtu;
	}


	public void setEmailEtu(String emailEtu) {
		this.emailEtu.set(emailEtu);
	}


	public IntegerProperty telEtuProperty() {
		return telEtu;
	}


	public void setTelEtu(int telEtu) {
		this.telEtu.set(telEtu);
	}


	public LocalDate getDateNaissEtu() {
		return dateNaissEtu;
	}


	public void setDateNaissEtu(LocalDate dateNaissEtu) {
		this.dateNaissEtu = dateNaissEtu;
	}


	public StringProperty sexeEtuProperty() {
		return sexeEtu;
	}


	public void setSexeEtu(String sexeEtu) {
		this.sexeEtu.set(sexeEtu);
	}


	public InputStream getPhotoEtu() {
		return photoEtu;
	}


	public void setPhotoEtu(InputStream photoEtu) {
		this.photoEtu = photoEtu;
	}


	public LocalDate getDateAjout() {
		return dateAjout;
	}


	public void setDateAjout(LocalDate dateAjout) {
		this.dateAjout = dateAjout;
	}


	public ListProperty<Inscription> inscriptionsProperty() {
		return inscriptions;
	}


	public void setInscriptions(ObservableList<Inscription> inscriptions) {
		this.inscriptions.set(inscriptions);;
	}


	public int getIdEtu() {
		return idEtu.get();
	}


	public String getNomEtu() {
		return nomEtu.get();
	}


	public String getPrenomEtu() {
		return prenomEtu.get();
	}


	public String getEmailEtu() {
		return emailEtu.get();
	}


	public int getTelEtu() {
		return telEtu.get();
	}


	public String getSexeEtu() {
		return sexeEtu.get();
	}


	public ObservableList<Inscription> getInscriptions() {
		return inscriptions.get();
	}
	
	
	public String toString() {
		return this.getNomEtu() + " " + this.getPrenomEtu();
	}
	
	
	
	
	
	
}
