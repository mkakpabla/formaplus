package com.formaplus.controllers.base;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.formaplus.utils.AlertMessage;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AbstractController {

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
	    Set<ConstraintViolation<AbstractController>> constraintViolations = validator.validate(this);
	    if(!constraintViolations.isEmpty()) {
	    	if (constraintViolations.size() > 0 ) {
	  	      for (ConstraintViolation<AbstractController> contraintes : constraintViolations) {
	  	        AlertMessage.showWarning(contraintes.getMessage());
	  	        return false;
	  	      }
	  	    } 
	    }
	    return true;
	}
}
