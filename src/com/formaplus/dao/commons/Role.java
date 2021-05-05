package com.formaplus.dao.commons;

public enum Role {
	
	ADMIN("Administrateur"),
	SECRET("Sécrétaire");
	
	private String label;
	
	Role(String label) {
		this.label = label;
	}
	
	public String toString() {
        return label;
    }

}
