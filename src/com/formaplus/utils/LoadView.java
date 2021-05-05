package com.formaplus.utils;

import java.io.File;
import java.io.IOException;

import com.formaplus.controllers.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoadView
{


	private static Scene scene;
	private static Stage stage;


	private static void initStatge()
	{
		if(stage==null){
	         stage = new Stage();
		}
	}
	
	
	public static void showModal(Window windows, String name, String title) 
	{
		stage = new Stage();
		stage.setScene(new Scene(LoadView.loadFxml(name)));
		stage.setTitle(title);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.initOwner(windows);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	
	public static Stage getModalStage(Control control, String viewName, Object obj, String title) 
	{
		
		FXMLLoader loader = LoadView.getFXMLLoader(viewName);
		Stage modalStage = new Stage();
		try {
			modalStage.setScene(new Scene(loader.load()));
			Controller controller = loader.getController();
			controller.setData(obj);
			
			modalStage.setTitle(title);
			modalStage.setResizable(false);
			modalStage.centerOnScreen();
			modalStage.initOwner(control.getScene().getWindow());
			modalStage.initModality(Modality.APPLICATION_MODAL);
			return modalStage;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public static Stage getModalStage(Control control, String viewName, String title) {
		FXMLLoader loader = LoadView.getFXMLLoader(viewName);
		Stage modalStage = new Stage();
		try {
			modalStage.setScene(new Scene(loader.load()));
			
			modalStage.setTitle(title);
			modalStage.setResizable(false);
			modalStage.centerOnScreen();
			modalStage.initOwner(control.getScene().getWindow());
			modalStage.initModality(Modality.APPLICATION_MODAL);
			return modalStage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void showModal(Control control, Stage stage, String title) 
	{
		stage.setResizable(false);
		stage.setTitle(title);
		stage.centerOnScreen();
		stage.initOwner(control.getScene().getWindow());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	
	


	public static void showView(String title, String name)
	{
		initStatge();
		scene = new Scene(loadFxml(name));
		stage.setScene(scene);
		stage.setTitle(title);
		stage.centerOnScreen();
		stage.show();
	}
	
	public static Parent loadFxml(String name) {
		try {
			return FXMLLoader.load(LoadView.class.getResource("/com/formaplus/views/" + name + ".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	public static FXMLLoader getFXMLLoader(String viewName) {
		return new FXMLLoader(LoadView.class.getResource("/com/formaplus/views/" + viewName + ".fxml"));
	}
	
	
	public static void showContent(AnchorPane root, String name)
	{
		Parent parent;
		try {
			parent = FXMLLoader.load(LoadView.class.getResource("/com/formaplus/views/" +name));
			root.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static File chooseImageDialog(Control control) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*.png", "*.jpg"));
		File file = fileChooser.showOpenDialog(control.getScene().getWindow());
		if(file != null) {
			return file;
		}
		return null;
	}
	
	


}
