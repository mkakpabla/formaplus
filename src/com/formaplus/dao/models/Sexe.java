package com.formaplus.dao.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sexe {
	
	
	
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ObservableList<Sexe> all() {
		String [] sexe =  {"Masculin", "Féminin"};
		ObservableList<Sexe> list = FXCollections.observableArrayList();
		for(String s: sexe) {
			Sexe se = new Sexe();
			se.setName(s);
			list.add(se);
		}
		
		return list;
		
	}
	
	
	
	

}
