package com.formaplus.utils;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertMessage {
	
	
	
	
	public static void showInformation(String titleBar, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Information");
		alert.setContentText(message);
		alert.showAndWait();
		
	}
	
	public static void showInformation(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Information");
		alert.setContentText(message);
		alert.showAndWait();
		
	}
	
	public static void showWarning(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation");
		alert.setHeaderText("Champ Invalide");
		alert.setContentText(message);
		alert.showAndWait();
		
	}
	
	
	public static ButtonType showConfirm(String titleBar, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titleBar);
		alert.setHeaderText(titleBar);
		alert.setContentText(message);
		return alert.showAndWait().get();
	}
	
	public static boolean showConfirm(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Confirmation");
		alert.setContentText(message);
		ButtonType result = alert.showAndWait().get();
		if(result == ButtonType.OK) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	

}
