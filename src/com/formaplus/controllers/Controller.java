package com.formaplus.controllers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.formaplus.utils.AlertMessage;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Controller {
	
	
	
	public void setData(Object obj) {
		
		
	}
	
	
	public void close(ActionEvent event) {
		Node  source = (Node)event.getSource(); 
	    Stage stage  = (Stage)source.getScene().getWindow();
	    stage.close();
	}
	
	
	
	public boolean validate() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<Controller>> constraintViolations = validator.validate(this);
	    if(!constraintViolations.isEmpty()) {
	    	if (constraintViolations.size() > 0 ) {
	  	      for (ConstraintViolation<Controller> contraintes : constraintViolations) {
	  	        AlertMessage.showWarning(contraintes.getMessage());
	  	        return false;
	  	      }
	  	    } 
	    }
	    return true;
	}

	

}
