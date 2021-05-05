package com.formaplus.dao.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Formation {
	
	
	private IntegerProperty idFormation;
	
	@NotBlank @NotNull
	private StringProperty  libFormation;
	
	@NotNull @Positive
	private IntegerProperty dureeFormation;
	
	@NotNull @Positive
	private DoubleProperty prixFormation;
	
	public CheckBox checkBoxFormation;
	

	public Formation(int idFormation, String libFormation, int dureeFormation, Double prixFormation,
			CheckBox checkBoxFormation) {
		this.idFormation = new SimpleIntegerProperty(idFormation);
		this.libFormation = new SimpleStringProperty(libFormation);
		this.dureeFormation = new SimpleIntegerProperty(dureeFormation);
		this.prixFormation = new SimpleDoubleProperty(prixFormation);
		this.checkBoxFormation = new CheckBox();
	}



	public Formation() {
		this.idFormation = new SimpleIntegerProperty(0);
		this.libFormation = new SimpleStringProperty(null);
		this.dureeFormation = new SimpleIntegerProperty(0);
		this.prixFormation = new SimpleDoubleProperty(0);
		this.checkBoxFormation = new CheckBox();
	}
	
	
	
	public void setIdFormation(int idFormation) {
		this.idFormation.set(idFormation);
	}
	
	public int getIdFormation() {
		return this.idFormation.get();
	}
	
	public IntegerProperty idFormationProperty() {
		return this.idFormation;
	}
	
	public void setLibFormation(String libFormation) {
		this.libFormation.set(libFormation);
	}
	
	public String getLibFormation() {
		return this.libFormation.get();
	}
	
	public StringProperty libFormationProperty() {
		return this.libFormation;
	}
	
	public void setDureeFormation(int dureeFormation) {
		this.dureeFormation.set(dureeFormation);
	}
	
	public int getDureeFormation() {
		return this.dureeFormation.get();
	}
	
	public IntegerProperty dureeFormationProperty() {
		return this.dureeFormation;
	}
	
	public void setPrixFormation(double prixFormation) {
		this.prixFormation.set(prixFormation);
	}
	
	public Double getPrixFormation() {
		return this.prixFormation.get();
	}
	
	public DoubleProperty prixFormationProperty() {
		return this.prixFormation;
	}



	public CheckBox getCheckBoxFormation() {
		return checkBoxFormation;
	}



	public void setCheckBoxFormation(CheckBox checkBoxFormation) {
		this.checkBoxFormation = checkBoxFormation;
	}
	
	@Override
	public String toString() {
		return this.getLibFormation();
		
	}
	
	
	
	

}
